package com.pcs.notification_service.templates;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandlebarsTemplateRenderer implements TemplateRenderer {

    private final Map<String, Template> compiledTemplates = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadTemplates() throws IOException {
        // Load all templates from /templates folder in resources
        String[] templateNames = {"APPOINTMENT_REMINDER"};
        Handlebars handlebars = new Handlebars();

        for (String name : templateNames) {
            String content = new String(
                    Objects.requireNonNull(
                            getClass().getClassLoader().getResourceAsStream("templates/" + name + ".hbs")
                    ).readAllBytes()
            );
            compiledTemplates.put(name, handlebars.compileInline(content));
            log.info("Loaded template for eventType={}", name);
        }
    }

    @Override
    public String render(String eventType, Map<String, String> payload) throws TemplateRenderException {
        Template template = compiledTemplates.get(eventType);
        if (template == null) {
            throw new TemplateRenderException("No template found for eventType: " + eventType);
        }
        try {
            return template.apply(payload);
        } catch (IOException e) {
            log.error("Template rendering failed for eventType={}", eventType, e);
            throw new TemplateRenderException(e.getMessage());
        }
    }
}

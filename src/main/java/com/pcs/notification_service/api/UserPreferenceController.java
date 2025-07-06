package com.pcs.notification_service.api;

import com.pcs.notification_service.persistence.UserPreferenceRepository;
import com.pcs.notification_service.persistence.entities.UserPreference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceRepository userPreferenceRepository;

    @PostMapping("/create")
    public ResponseEntity<UserPreference> createUserPreference(@RequestBody UserPreference userPreference) {
        // Logic to create user preference
        UserPreference savedPreference = userPreferenceRepository.save(userPreference);
        return ResponseEntity.ok(savedPreference);
    }
}

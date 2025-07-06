package com.pcs.notification_service.api;

import com.pcs.notification_service.persistence.DeliveryRecordRepository;
import com.pcs.notification_service.persistence.entities.DeliveryRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DeliveryRecordRepository deliveryRecordRepository;

    @GetMapping("/deliveries")
    public Page<DeliveryRecord> getDeliveries(
            @RequestParam Optional<String> userId,
            @RequestParam Optional<String> status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Specification<DeliveryRecord> spec = Specification.where(null);

        if (userId.isPresent()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("userId"), userId.get()));
        }

        if (status.isPresent()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status.get()));
        }

        return deliveryRecordRepository.findAll(spec, pageable);
    }
}

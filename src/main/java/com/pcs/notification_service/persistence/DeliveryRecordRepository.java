package com.pcs.notification_service.persistence;

import com.pcs.notification_service.persistence.entities.DeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long>, JpaSpecificationExecutor<DeliveryRecord> {
}

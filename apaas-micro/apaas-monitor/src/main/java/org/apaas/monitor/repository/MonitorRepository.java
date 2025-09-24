package org.apaas.monitor.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.monitor.entity.MonitorEntity;
import org.springframework.stereotype.Repository;

/**
 * @author ivan
 */
@Repository
public interface MonitorRepository extends ReactiveBaseRepository<MonitorEntity, Long> {
}
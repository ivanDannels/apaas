package org.apaas.monitor.repository;

import org.apaas.monitor.entity.MonitorEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorRepository extends R2dbcRepository<MonitorEntity, Long> {
}
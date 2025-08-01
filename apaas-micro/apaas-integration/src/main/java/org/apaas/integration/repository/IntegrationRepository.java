package org.apaas.integration.repository;

import org.apaas.integration.entity.IntegrationEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationRepository extends R2dbcRepository<IntegrationEntity, Long> {
}
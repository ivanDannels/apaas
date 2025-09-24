package org.apaas.integration.repository;

import org.apaas.integration.entity.IntegrationEntity;
import org.apaas.domain.repository.ReactiveBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationRepository extends ReactiveBaseRepository<IntegrationEntity, Long> {
}
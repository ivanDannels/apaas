package org.apaas.system.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.system.entity.Files;
import org.springframework.stereotype.Repository;

/**
 * 文件仓库
 * @author ivan
 */
@Repository
public interface FilesRepository extends ReactiveBaseRepository<Files, Long> {
}

package org.apaas.system.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author ivan
 */
@Data
@SuperBuilder
@Table("files")
public class Files extends BaseEntity {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
    private String filePath;
    private String fileMd5;
}

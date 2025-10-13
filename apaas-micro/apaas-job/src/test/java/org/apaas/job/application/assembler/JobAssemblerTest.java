package org.apaas.job.application.assembler;

import org.apaas.job.domain.model.JobEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JobAssemblerTest {
    
    private final JobAssembler assembler = Mappers.getMapper(JobAssembler.class);
    
    @Test
    void testToEntity() {
        // 创建实体对象
        JobEntity entity = new JobEntity();
        entity.setId(1L);
        entity.setName("测试任务");
        entity.setDescription("测试任务描述");
        entity.setCronExpression("0 0 12 * * ?");
        entity.setStatus(1);
        entity.setCreatedBy("admin");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdatedBy("admin");
        entity.setUpdatedTime(LocalDateTime.now());
        
        // 转换实体
        JobEntity result = assembler.toEntity(entity);
        
        // 验证转换结果
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getDescription(), result.getDescription());
        assertEquals(entity.getCronExpression(), result.getCronExpression());
        assertEquals(entity.getStatus(), result.getStatus());
        assertEquals(entity.getCreatedBy(), result.getCreatedBy());
    }
    
    @Test
    void testToDTO() {
        // 创建实体对象
        JobEntity entity = new JobEntity();
        entity.setId(1L);
        entity.setName("测试任务");
        entity.setDescription("测试任务描述");
        entity.setCronExpression("0 0 12 * * ?");
        entity.setStatus(1);
        entity.setCreatedBy("admin");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdatedBy("admin");
        entity.setUpdatedTime(LocalDateTime.now());
        
        // 转换为DTO
        JobEntity dto = assembler.toDTO(entity);
        
        // 验证转换结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getCronExpression(), dto.getCronExpression());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getCreatedBy(), dto.getCreatedBy());
    }
}
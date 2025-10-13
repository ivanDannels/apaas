package org.apaas.form.engine.application.assembler;

import org.apaas.form.engine.domain.model.FormDefinition;
import org.apaas.form.engine.application.dto.FormDefinitionDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormDefinitionAssemblerTest {
    
    private final FormDefinitionAssembler assembler = Mappers.getMapper(FormDefinitionAssembler.class);
    
    @Test
    void testToDTO() {
        // 创建实体对象
        FormDefinition entity = FormDefinition.builder()
                .id(1L)
                .name("测试表单")
                .description("测试表单描述")
                .version(1)
                .formSchema("表单结构")
                .status(1)
                .createdBy("admin")
                .createdTime(LocalDateTime.now())
                .updatedBy("admin")
                .updatedTime(LocalDateTime.now())
                .build();
        
        // 转换为DTO
        FormDefinitionDTO dto = assembler.toDTO(entity);
        
        // 验证转换结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getVersion(), dto.getVersion());
        assertEquals(entity.getFormSchema(), dto.getFormSchema());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getCreatedBy(), dto.getCreatedBy());
    }
    
    @Test
    void testToEntity() {
        // 创建DTO对象
        FormDefinitionDTO dto = new FormDefinitionDTO();
        dto.setId(1L);
        dto.setName("测试表单");
        dto.setDescription("测试表单描述");
        dto.setVersion(1);
        dto.setFormSchema("表单结构");
        dto.setStatus(1);
        dto.setCreatedBy("admin");
        dto.setCreatedTime(LocalDateTime.now());
        dto.setUpdatedBy("admin");
        dto.setUpdatedTime(LocalDateTime.now());
        
        // 转换为实体
        FormDefinition entity = assembler.toEntity(dto);
        
        // 验证转换结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getVersion(), entity.getVersion());
        assertEquals(dto.getFormSchema(), entity.getFormSchema());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getCreatedBy(), entity.getCreatedBy());
    }
    
    @Test
    void testToDTOList() {
        // 创建实体列表
        List<FormDefinition> entities = Arrays.asList(
                FormDefinition.builder().id(1L).name("表单1").build(),
                FormDefinition.builder().id(2L).name("表单2").build()
        );
        
        // 转换为DTO列表
        List<FormDefinitionDTO> dtos = assembler.toDTOList(entities);
        
        // 验证转换结果
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getId(), dtos.get(0).getId());
        assertEquals(entities.get(1).getId(), dtos.get(1).getId());
    }
    
    @Test
    void testToEntityList() {
        // 创建DTO列表
        List<FormDefinitionDTO> dtos = Arrays.asList(
                new FormDefinitionDTO() {{ setId(1L); setName("表单1"); }},
                new FormDefinitionDTO() {{ setId(2L); setName("表单2"); }}
        );
        
        // 转换为实体列表
        List<FormDefinition> entities = assembler.toEntityList(dtos);
        
        // 验证转换结果
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dtos.get(0).getId(), entities.get(0).getId());
        assertEquals(dtos.get(1).getId(), entities.get(1).getId());
    }
}
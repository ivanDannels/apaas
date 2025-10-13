/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apaas.design.platform.application.assembler;

import org.apaas.design.platform.application.dto.CodeTemplateDTO;
import org.apaas.design.platform.domain.model.CodeTemplate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 代码模板装配器测试类
 *
 * @author ivan
 */
class CodeTemplateAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        CodeTemplateDTO dto = new CodeTemplateDTO();
        dto.setId(1L);
        dto.setName("Java实体类模板");
        dto.setContent("public class ${className} {\n${fields}\n}");
        dto.setLanguage("java");
        dto.setDescription("生成Java实体类的模板");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 执行转换
        CodeTemplate entity = CodeTemplateAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getContent(), entity.getContent());
        assertEquals(dto.getLanguage(), entity.getLanguage());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        CodeTemplate entity = new CodeTemplate();
        entity.setId(1L);
        entity.setName("Java实体类模板");
        entity.setContent("public class ${className} {\n${fields}\n}");
        entity.setLanguage("java");
        entity.setDescription("生成Java实体类的模板");
        entity.setStatus(0);
        entity.setTenantId(1L);

        // 执行转换
        CodeTemplateDTO dto = CodeTemplateAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getContent(), dto.getContent());
        assertEquals(entity.getLanguage(), dto.getLanguage());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        CodeTemplate entity1 = new CodeTemplate();
        entity1.setId(1L);
        entity1.setName("Java实体类模板");
        entity1.setContent("public class ${className} {\n${fields}\n}");
        entity1.setLanguage("java");
        entity1.setDescription("生成Java实体类的模板");
        entity1.setStatus(0);
        entity1.setTenantId(1L);

        CodeTemplate entity2 = new CodeTemplate();
        entity2.setId(2L);
        entity2.setName("Vue页面模板");
        entity2.setContent("<template>\n  <div>\n    ${content}\n  </div>\n</template>");
        entity2.setLanguage("vue");
        entity2.setDescription("生成Vue页面的模板");
        entity2.setStatus(1);
        entity2.setTenantId(1L);

        List<CodeTemplate> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<CodeTemplateDTO> dtoList = CodeTemplateAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}
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
package org.apaas.system.application.assembler;

import org.apaas.system.application.dto.InternationalizationDTO;
import org.apaas.system.domain.model.Internationalization;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 国际化装配器测试类
 *
 * @author ivan
 */
class InternationalizationAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        InternationalizationDTO dto = new InternationalizationDTO();
        dto.setId(1L);
        dto.setCode("sys.login.title");
        dto.setMessage("APaaS Platform");
        dto.setLanguage("en");
        dto.setCountry("US");
        dto.setRegion("");
        dto.setLocale("en_US");
        dto.setTimeZone("UTC");
        dto.setCurrency("USD");
        dto.setCurrencySymbol("$");
        dto.setCurrencyCode("USD");
        dto.setCurrencySymbolPosition("before");
        dto.setCurrencyDecimalSeparator(".");
        dto.setCurrencyGroupingSeparator(",");
        dto.setCurrencyGroupingSize(3);
        dto.setCurrencyGroupingCount(1);
        dto.setCurrencyFormat("#,##0.00");
        dto.setCurrencyFormatSymbols("$#,##0.00");
        dto.setTenantId(1L);

        // 执行转换
        Internationalization entity = InternationalizationAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getMessage(), entity.getMessage());
        assertEquals(dto.getLanguage(), entity.getLanguage());
        assertEquals(dto.getCountry(), entity.getCountry());
        assertEquals(dto.getRegion(), entity.getRegion());
        assertEquals(dto.getLocale(), entity.getLocale());
        assertEquals(dto.getTimeZone(), entity.getTimeZone());
        assertEquals(dto.getCurrency(), entity.getCurrency());
        assertEquals(dto.getCurrencySymbol(), entity.getCurrencySymbol());
        assertEquals(dto.getCurrencyCode(), entity.getCurrencyCode());
        assertEquals(dto.getCurrencySymbolPosition(), entity.getCurrencySymbolPosition());
        assertEquals(dto.getCurrencyDecimalSeparator(), entity.getCurrencyDecimalSeparator());
        assertEquals(dto.getCurrencyGroupingSeparator(), entity.getCurrencyGroupingSeparator());
        assertEquals(dto.getCurrencyGroupingSize(), entity.getCurrencyGroupingSize());
        assertEquals(dto.getCurrencyGroupingCount(), entity.getCurrencyGroupingCount());
        assertEquals(dto.getCurrencyFormat(), entity.getCurrencyFormat());
        assertEquals(dto.getCurrencyFormatSymbols(), entity.getCurrencyFormatSymbols());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        Internationalization entity = new Internationalization();
        entity.setId(1L);
        entity.setCode("sys.login.title");
        entity.setMessage("APaaS Platform");
        entity.setLanguage("en");
        entity.setCountry("US");
        entity.setRegion("");
        entity.setLocale("en_US");
        entity.setTimeZone("UTC");
        entity.setCurrency("USD");
        entity.setCurrencySymbol("$");
        entity.setCurrencyCode("USD");
        entity.setCurrencySymbolPosition("before");
        entity.setCurrencyDecimalSeparator(".");
        entity.setCurrencyGroupingSeparator(",");
        entity.setCurrencyGroupingSize(3);
        entity.setCurrencyGroupingCount(1);
        entity.setCurrencyFormat("#,##0.00");
        entity.setCurrencyFormatSymbols("$#,##0.00");
        entity.setTenantId(1L);

        // 执行转换
        InternationalizationDTO dto = InternationalizationAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getMessage(), dto.getMessage());
        assertEquals(entity.getLanguage(), dto.getLanguage());
        assertEquals(entity.getCountry(), dto.getCountry());
        assertEquals(entity.getRegion(), dto.getRegion());
        assertEquals(entity.getLocale(), dto.getLocale());
        assertEquals(entity.getTimeZone(), dto.getTimeZone());
        assertEquals(entity.getCurrency(), dto.getCurrency());
        assertEquals(entity.getCurrencySymbol(), dto.getCurrencySymbol());
        assertEquals(entity.getCurrencyCode(), dto.getCurrencyCode());
        assertEquals(entity.getCurrencySymbolPosition(), dto.getCurrencySymbolPosition());
        assertEquals(entity.getCurrencyDecimalSeparator(), dto.getCurrencyDecimalSeparator());
        assertEquals(entity.getCurrencyGroupingSeparator(), dto.getCurrencyGroupingSeparator());
        assertEquals(entity.getCurrencyGroupingSize(), dto.getCurrencyGroupingSize());
        assertEquals(entity.getCurrencyGroupingCount(), dto.getCurrencyGroupingCount());
        assertEquals(entity.getCurrencyFormat(), dto.getCurrencyFormat());
        assertEquals(entity.getCurrencyFormatSymbols(), dto.getCurrencyFormatSymbols());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        Internationalization entity1 = new Internationalization();
        entity1.setId(1L);
        entity1.setCode("sys.login.title");
        entity1.setMessage("APaaS Platform");
        entity1.setLanguage("en");
        entity1.setCountry("US");
        entity1.setRegion("");
        entity1.setLocale("en_US");
        entity1.setTimeZone("UTC");
        entity1.setCurrency("USD");
        entity1.setCurrencySymbol("$");
        entity1.setCurrencyCode("USD");
        entity1.setCurrencySymbolPosition("before");
        entity1.setCurrencyDecimalSeparator(".");
        entity1.setCurrencyGroupingSeparator(",");
        entity1.setCurrencyGroupingSize(3);
        entity1.setCurrencyGroupingCount(1);
        entity1.setCurrencyFormat("#,##0.00");
        entity1.setCurrencyFormatSymbols("$#,##0.00");
        entity1.setTenantId(1L);

        Internationalization entity2 = new Internationalization();
        entity2.setId(2L);
        entity2.setCode("sys.login.title");
        entity2.setMessage("APaaS平台");
        entity2.setLanguage("zh");
        entity2.setCountry("CN");
        entity2.setRegion("");
        entity2.setLocale("zh_CN");
        entity2.setTimeZone("Asia/Shanghai");
        entity2.setCurrency("CNY");
        entity2.setCurrencySymbol("¥");
        entity2.setCurrencyCode("CNY");
        entity2.setCurrencySymbolPosition("before");
        entity2.setCurrencyDecimalSeparator(".");
        entity2.setCurrencyGroupingSeparator(",");
        entity2.setCurrencyGroupingSize(3);
        entity2.setCurrencyGroupingCount(1);
        entity2.setCurrencyFormat("#,##0.00");
        entity2.setCurrencyFormatSymbols("¥#,##0.00");
        entity2.setTenantId(1L);

        List<Internationalization> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<InternationalizationDTO> dtoList = InternationalizationAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getMessage(), dtoList.get(0).getMessage());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getMessage(), dtoList.get(1).getMessage());
    }
}
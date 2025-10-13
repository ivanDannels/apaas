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
package org.apaas.system.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 国际化DTO测试类
 *
 * @author ivan
 */
class InternationalizationDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        InternationalizationDTO dto = new InternationalizationDTO();
        Long id = 1L;
        String code = "sys.login.title";
        String message = "APaaS Platform";
        String language = "en";
        String country = "US";
        String region = "";
        String locale = "en_US";
        String timeZone = "UTC";
        String currency = "USD";
        String currencySymbol = "$";
        String currencyCode = "USD";
        String currencySymbolPosition = "before";
        String currencyDecimalSeparator = ".";
        String currencyGroupingSeparator = ",";
        Integer currencyGroupingSize = 3;
        Integer currencyGroupingCount = 1;
        String currencyFormat = "#,##0.00";
        String currencyFormatSymbols = "$#,##0.00";
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setCode(code);
        dto.setMessage(message);
        dto.setLanguage(language);
        dto.setCountry(country);
        dto.setRegion(region);
        dto.setLocale(locale);
        dto.setTimeZone(timeZone);
        dto.setCurrency(currency);
        dto.setCurrencySymbol(currencySymbol);
        dto.setCurrencyCode(currencyCode);
        dto.setCurrencySymbolPosition(currencySymbolPosition);
        dto.setCurrencyDecimalSeparator(currencyDecimalSeparator);
        dto.setCurrencyGroupingSeparator(currencyGroupingSeparator);
        dto.setCurrencyGroupingSize(currencyGroupingSize);
        dto.setCurrencyGroupingCount(currencyGroupingCount);
        dto.setCurrencyFormat(currencyFormat);
        dto.setCurrencyFormatSymbols(currencyFormatSymbols);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(code, dto.getCode());
        assertEquals(message, dto.getMessage());
        assertEquals(language, dto.getLanguage());
        assertEquals(country, dto.getCountry());
        assertEquals(region, dto.getRegion());
        assertEquals(locale, dto.getLocale());
        assertEquals(timeZone, dto.getTimeZone());
        assertEquals(currency, dto.getCurrency());
        assertEquals(currencySymbol, dto.getCurrencySymbol());
        assertEquals(currencyCode, dto.getCurrencyCode());
        assertEquals(currencySymbolPosition, dto.getCurrencySymbolPosition());
        assertEquals(currencyDecimalSeparator, dto.getCurrencyDecimalSeparator());
        assertEquals(currencyGroupingSeparator, dto.getCurrencyGroupingSeparator());
        assertEquals(currencyGroupingSize, dto.getCurrencyGroupingSize());
        assertEquals(currencyGroupingCount, dto.getCurrencyGroupingCount());
        assertEquals(currencyFormat, dto.getCurrencyFormat());
        assertEquals(currencyFormatSymbols, dto.getCurrencyFormatSymbols());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        InternationalizationDTO dto = new InternationalizationDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
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

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("InternationalizationDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("code=sys.login.title"));
        assertTrue(toStringResult.contains("message=APaaS Platform"));
    }
}
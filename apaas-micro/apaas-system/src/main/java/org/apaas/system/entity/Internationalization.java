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
package org.apaas.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@Table("internationalization")
@EqualsAndHashCode(callSuper = true)
public class Internationalization extends BaseEntity {
    
    private String code;
    private String message;
    private String language;
    private String country;
    private String region;
    private String locale;
    private String timeZone;
    private String currency;
    private String currencySymbol;
    private String currencyCode;
    private String currencySymbolPosition;
    private String currencyDecimalSeparator;
    private String currencyGroupingSeparator;
    private String currencyGroupingSize;
    private String currencyGroupingCount;
    private String currencyFormat;
    private String currencyFormatSymbols;
    
}

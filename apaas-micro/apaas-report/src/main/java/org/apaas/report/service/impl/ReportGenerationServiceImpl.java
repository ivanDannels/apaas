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
package org.apaas.report.service.impl;

import org.apaas.report.entity.Report;
import org.apaas.report.service.ReportGenerationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportGenerationServiceImpl implements ReportGenerationService {
    
    @Override
    public byte[] generatePdfReport(String reportTemplate, List<?> data) {
        return null;
    }
    
    @Override
    public byte[] generateExcelReport(List<Report> reports) {
        // Implementation for Excel report generation
        // This would use Apache POI to create an Excel file
        return new byte[0];
    }
}
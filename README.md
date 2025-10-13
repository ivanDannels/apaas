欢迎使用 APaaS（Application Platform as a Service）平台——这是一个全面的企业级云原生智能应用构建平台，旨在通过零代码/低代码方式快速开发数据驱动的业务应用程序。

## 平台愿景

APaaS 定位为企业的分布式智能平台，提供统一的数据调度、集成、治理、分析和应用能力。该平台通过工作流引擎动态驱动业务流程，支持分布式任务处理、审批工作流和有向无环图（DAG）流引擎，并具备在线 API 开发、数据模型设计和数据集成能力 [docs/APaaS平台详细设计文档.md]。

## 架构概览

APaaS 平台采用现代化的微服务架构，具有清晰的关注点分离：

## 项目结构

平台按照 Maven 多模块结构组织成不同的模块：

| 模块                 | 用途           | 关键技术                                 |
| -------------------- | -------------- | ---------------------------------------- |
| **apaas-ui**         | 前端应用       | Vue 3, Element Plus, Pinia, TypeScript   |
| **apaas-gateway**    | API 网关和路由 | Spring Cloud Gateway, WebFlux            |
| **apaas-micro**      | 核心微服务     | Spring Boot 3.5.6, Spring Cloud 2025.0.0 |
| **apaas-framework**  | 共享框架组件   | 领域驱动设计, 工具集                     |
| **apaas-api**        | API 契约和定义 | OpenAPI 3.0, 响应式编程                  |
| **apaas-standalone** | 单机部署选项   | 嵌入式服务配置                           |

## 核心服务概览

### 业务流程引擎

- **流引擎服务**：工作流设计、审批流程、支持版本控制的 DAG 建模 [apaas-micro/apaas-flow-execution/README.md]
- **流执行服务**：运行时流程实例管理、任务执行和工作流编排 [apaas-micro/apaas-flow-execution/README.md]
- **表单引擎服务**：支持多字段类型、验证规则和布局管理的动态表单生成

### 数据和集成服务

- **数据服务**：数据模型设计、多数据源管理和处理流水线
- **集成服务**：API、表单、认证和业务系统集成能力
- **作业调度服务**：具有多种执行策略和错误处理的分布式任务调度

### 平台服务

- **认证服务**：多租户支持、RBAC 授权和组织管理
- **系统服务**：数据字典、配置管理、国际化和通知
- **监控服务**：操作审计、工作流跟踪、异常监控和日志分析

### AI 和高级功能

- **AI 助手服务**：企业 AI 集成和智能辅助能力
- **知识服务**：知识管理和检索系统
- **报表服务**：工作流分析、自定义报表和仪表板监控

## 技术栈

### 后端技术

- **框架**：Spring Boot 3.5.6 与 Spring Cloud 2025.0.0 [pom.xml]
- **编程**：Java 25 与响应式编程 (WebFlux) [pom.xml]
- **数据库**：PostgreSQL 与 R2DBC 响应式数据访问 [pom.xml]
- **缓存**：Redis 与 Redisson 集成 [pom.xml]
- **消息**：RabbitMQ 异步通信 [pom.xml]
- **服务发现**：Nacos 服务注册和配置管理
- **AI 集成**：Spring AI 1.0.2 支持 Ollama 和 Transformers [pom.xml]

### 前端技术

- **框架**：Vue 3.5.22 与组合式 API [apaas-ui/package.json]
- **UI 库**：Element Plus 2.11.4 完整组件套件 [apaas-ui/package.json]
- **状态管理**：Pinia 3.0.3 响应式状态管理 [apaas-ui/package.json]
- **构建工具**：Vite 7.1.7 快速开发和构建 [apaas-ui/package.json]
- **国际化**：Vue I18n 11.1.12 多语言支持 [apaas-ui/package.json]

## 核心功能

### 零代码/低代码开发

- 可视化工作流设计器，支持拖放界面
- 具有高级验证规则的动态表单构建器
- 支持关系映射的数据模型设计器
- API 定义和测试工具

### 企业级能力

- **多租户架构**：完整的数据隔离和资源管理 [docs/APaaS平台详细设计文档.md]
- **安全框架**：零信任安全模型，端到端加密 [docs/APaaS平台详细设计文档.md]
- **高可用性**：负载均衡、故障转移和灾难恢复能力 [docs/APaaS平台详细设计文档.md]
- **可观测性**：全面的监控、日志和分布式追踪 [docs/APaaS平台详细设计文档.md]

### AI 驱动的智能

- 用于流程优化的企业 AI 助手
- RAG（检索增强生成）能力
- 智能工作流建议和自动化

## 快速开始

平台提供 Docker Compose 配置以便于部署 [docker-compose.yml]：

BASH

```plain
git clone https://github.com/ivanDannels/apaas.git
cd apaas
 
# 启动所有服务
docker-compose up
 
# 访问服务
# 集成服务: http://localhost:8081
# 作业服务: http://localhost:8082
# 监控服务: http://localhost:8083
# Nacos 控制台: http://localhost:8848
```
# APaaS平台核心模块

APaaS平台核心模块（apaas-core）是整个平台的基础模块，包含了各个业务模块共用的功能和配置。

## 功能特性

- 统一的异常处理机制
- 通用的工具类（JwtUtils、RedisUtils等）
- 基础实体类和仓储类（BaseEntity、BaseRepository等）
- 通用配置类（Redis配置、JPA配置等）
- 安全配置（Spring Security）
- Web配置
- OpenAPI配置

## 响应式重构说明

本模块已按照Spring Boot WebFlux响应式模式和DDD领域驱动架构进行重构，主要变更包括：

1. **数据访问层**:
   - BaseRepository和BaseEntityRepository接口已更新为响应式模式，使用Mono/Flux作为返回类型
   - RedisRepository已优化，提供完整的响应式Redis操作
   - 新增了对R2DBC的支持，提供响应式数据库访问能力

2. **服务层**:
   - BaseService和BaseServiceImpl已重构为响应式模式
   - 新增了领域服务接口和实现示例
   - 集成了基于Redis的领域事件发布机制

3. **领域层**:
   - BaseEntity已适配响应式数据访问
   - 新增了领域事件相关类

4. **应用层**:
   - 提供了响应式控制器示例

## 新增依赖项

为了减少其他模块的重复依赖，apaas-core模块现在包含以下依赖项：

- spring-cloud-starter-gateway
- spring-cloud-starter-loadbalancer
- spring-cloud-starter-alibaba-nacos-discovery
- spring-cloud-starter-alibaba-nacos-config
- spring-boot-starter-actuator
- commons-lang3
- hutool-all

这些依赖项可以在其他模块中直接使用，无需重复声明。

## 使用方法

1. **数据访问**:
   - 继承BaseEntityRepository接口获取基础数据访问能力
   - 直接使用RedisRepository进行Redis操作

2. **业务逻辑**:
   - 继承BaseServiceImpl实现业务逻辑
   - 实现DomainService处理复杂领域逻辑

3. **事件处理**:
   - 使用RedisDomainEventPublisher发布领域事件

## 依赖说明

- spring-boot-starter-webflux: 响应式Web框架
- spring-boot-starter-data-redis: Redis数据访问
- spring-boot-starter-data-r2dbc: 响应式数据库访问

## 测试说明

本模块包含了完整的测试代码，覆盖了DDD领域驱动的每一层：

- **实体层测试**: BaseEntityTest.java
- **仓储层测试**: ReactiveBaseRepositoryTest.java
- **服务层测试**: BaseServiceImplTest.java
- **控制层测试**: TestEntityControllerTest.java
- **集成测试**: IntegrationTest.java

测试使用了Testcontainers来提供真实的数据库环境，确保测试的准确性。

## 已移除重复依赖的模块

- `apaas-integration`
- `apaas-job`
- `apaas-monitor`
- `apaas-gateway`
- `apaas-report`
- `apaas-system`
- `apaas-auth`
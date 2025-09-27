# APaaS System 模块 DDD 重构说明

## 重构概述

本模块已根据领域驱动设计（DDD）原则进行重构，以更好地分离关注点并提高代码的可维护性和可扩展性。

## 主要改进

### 1. 引入聚合根模式
- 创建了 `DataDictionaryAggregate` 类作为数据字典的聚合根
- 明确标识了聚合根的边界和职责
- 聚合根负责维护内部一致性约束

### 2. 引入值对象模式
- 创建了 `DataDictionaryCode` 值对象，用于表示数据字典编码
- 值对象具有不可变性和基于属性的相等性判断

### 3. 明确服务分层
- **应用服务层**：`DataDictionaryApplicationService` 处理应用层逻辑，协调领域服务和基础设施
- **领域服务层**：`DataDictionaryDomainService` 处理复杂的业务逻辑和领域规则验证
- **传统服务接口**：`ReactiveDataDictionaryService` 作为适配器，保持向后兼容性

### 4. 引入工厂模式
- 创建了 `DataDictionaryFactory` 用于封装复杂对象的创建逻辑
- 确保创建的对象处于一致状态

### 5. 引入规范模式
- 创建了 `DataDictionarySpecification` 用于封装可复用的业务规则
- 支持规则的组合（AND、OR、NOT）

### 6. 仓库模式优化
- 创建了 `DataDictionaryAggregateRepository` 专门用于聚合根的持久化
- 保持了与原有 `DataDictionaryRepository` 的兼容性

## 包结构说明

```
org.apaas.system
├── config                  # 配置类
│   ├── DataDictionaryConfig.java          # 数据字典配置
│   └── MinioConfig.java                  # Minio配置
├── entity                  # 实体和聚合根
│   ├── DataDictionary.java               # 原始实体（保持兼容性）
│   ├── DataDictionaryAggregate.java      # 聚合根
│   ├── DataDictionaryCode.java           # 值对象
│   └── DataDictionaryItem.java           # 数据字典项实体
├── factory                 # 工厂类
│   └── DataDictionaryFactory.java
├── specification           # 规范类
│   └── DataDictionarySpecification.java
├── repository              # 仓库层
│   ├── DataDictionaryRepository.java         # 原始仓库接口
│   └── DataDictionaryAggregateRepository.java # 聚合根仓库接口
├── service                 # 服务层
│   ├── DataDictionaryApplicationService.java     # 应用服务接口
│   ├── DataDictionaryDomainService.java          # 领域服务接口
│   ├── ReactiveDataDictionaryService.java        # 传统服务接口（适配器）
│   └── impl
│       ├── DataDictionaryApplicationServiceImpl.java  # 应用服务实现
│       ├── DataDictionaryDomainServiceImpl.java       # 领域服务实现
│       └── ReactiveDataDictionaryServiceImpl.java     # 传统服务实现（适配器）
├── rest                    # REST控制器
│   └── DataDictionaryController.java
├── domain                  # 领域层
│   └── dto
│       └── DataDictionaryDTO.java
└── SystemApplication.java  # 应用启动类
```

## 使用示例

### 创建数据字典
```java
// 使用工厂创建聚合根
DataDictionaryFactory factory = new DataDictionaryFactory();
DataDictionaryAggregate dictionary = factory.create(null, "用户状态", "USER_STATUS", 1, "用户状态字典");

// 使用领域服务创建数据字典
DataDictionaryDomainService domainService = ...;
Mono<DataDictionaryAggregate> result = domainService.createDictionary(dictionary);
```

### 应用服务使用
```java
@RestController
public class DataDictionaryController {
    
    @Autowired
    private DataDictionaryApplicationService dataDictionaryApplicationService;
    
    @PostMapping("/dictionaries")
    public Mono<Boolean> createDictionary(@RequestBody DataDictionaryAggregate dictionary) {
        return dataDictionaryApplicationService.create(dictionary);
    }
}
```

## 向后兼容性

为了保持向后兼容性，我们保留了原有的 `ReactiveDataDictionaryService` 接口和实现，但将其重构为适配器模式，内部委托给新的应用服务实现。

这样既保持了现有代码的兼容性，又引入了DDD的最佳实践。

## 依赖注入

所有服务都通过Spring的组件扫描自动注册为Bean。工厂类通过配置类手动注册为Bean。
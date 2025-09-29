# DDD领域模型工程新包结构说明

为了更好地遵循领域驱动设计（DDD）的原则，我们对[apaas-domain](file:///d:/Projects/apaas/apaas-framework/apaas-domain/src/main/java/org/apaas/domain/specification/Specification.java#L3-L3)工程的包结构进行了重构，采用了更加清晰的分层架构。

## 新的包结构

```
org.apaas.domain
├── domain                  # 领域层 - 核心业务逻辑
│   ├── aggregate           # 聚合根相关
│   ├── entity             # 实体类
│   ├── vo                 # 值对象
│   ├── repository         # 仓储接口
│   ├── service            # 领域服务
│   ├── event              # 领域事件
│   ├── factory            # 工厂类
│   ├── specification      # 业务规则规范
│   └── exception          # 领域异常
├── application            # 应用层 - 协调领域对象完成用例
│   ├── service            # 应用服务
│   ├── dto                # 数据传输对象
│   └── assembler          # 装配器（DTO与领域对象转换）
├── infrastructure         # 基础设施层 - 技术实现细节
│   ├── config             # 配置类
│   ├── repository         # 仓储实现
│   │   └── impl          # 仓储具体实现
│   ├── event              # 事件处理
│   │   └── handler       # 事件处理器
│   ├── lock               # 分布式锁实现
│   ├── cache              # 缓存实现
│   ├── exception          # 异常处理
│   │   └── handler       # 异常处理器
│   ├── interceptor        # 拦截器
│   ├── convert            # 转换器
│   ├── log                # 日志工具
│   └── utils              # 工具类
└── interfaces             # 接口层 - 外部交互
    ├── rest               # REST控制器
    ├── event              # 事件监听
    │   └── listener      # 事件监听器
    └── rpc                # RPC接口
```

## 各层职责说明

### 领域层（domain）
包含核心业务逻辑，是DDD的核心部分：
- **aggregate**: 聚合根定义，如[AggregateRoot](file:///d:/Projects/apaas/apaas-framework/apaas-domain/src/main/java/org/apaas/domain/domain/aggregate/AggregateRoot.java#L27-L44)
- **entity**: 实体类，如[Order](file:///d:/Projects/apaas/apaas-framework/apaas-domain/src/main/java/org/apaas/domain/domain/entity/Order.java#L34-L139)、[OrderItem](file:///d:/Projects/apaas/apaas-framework/apaas-domain/src/main/java/org/apaas/domain/domain/entity/OrderItem.java#L24-L74)
- **vo**: 值对象，如[Money](file:///d:/Projects/apaas/apaas-framework/apaas-domain/src/main/java/org/apaas/domain/domain/vo/Money.java#L34-L168)、[BaseValueObject](file:///d:/Projects/apaas/apaas-framework/apaas-domain/src/main/java/org/apaas/domain/domain/vo/BaseValueObject.java#L26-L69)
- **repository**: 仓储接口定义
- **service**: 领域服务，处理跨实体的复杂业务逻辑
- **event**: 领域事件定义
- **factory**: 工厂类，用于创建复杂的领域对象
- **specification**: 业务规则规范，封装业务约束
- **exception**: 领域层异常定义

### 应用层（application）
协调领域对象完成业务用例：
- **service**: 应用服务，负责用例的编排和事务管理
- **dto**: 数据传输对象，用于与外部系统交互
- **assembler**: 装配器，负责DTO与领域对象之间的转换

### 基础设施层（infrastructure）
提供技术实现细节：
- **config**: 配置类，如缓存配置、数据库配置等
- **repository/impl**: 仓储的具体实现
- **event/handler**: 事件处理器的具体实现
- **lock**: 分布式锁的具体实现
- **cache**: 缓存的具体实现
- **exception/handler**: 异常处理器的具体实现
- **interceptor**: 拦截器的具体实现
- **convert**: 各种转换器的实现
- **log**: 日志工具的具体实现
- **utils**: 通用工具类

### 接口层（interfaces）
处理外部交互：
- **rest**: REST控制器，处理HTTP请求
- **event/listener**: 事件监听器，监听领域事件
- **rpc**: RPC接口，处理远程过程调用

## 迁移说明

为了保持向后兼容性，旧的包结构暂时保留，但标记为废弃。请逐步迁移到新的包结构。

新的类导入示例：
```java
// 旧的导入方式
import org.apaas.domain.entity.Order;
import org.apaas.domain.repository.OrderRepository;

// 新的导入方式
import org.apaas.domain.domain.entity.Order;
import org.apaas.domain.domain.repository.OrderRepository;
```

## 注意事项

1. 请在开发新功能时使用新的包结构
2. 在修改现有代码时，建议同时进行包结构调整
3. 旧的包结构将在未来版本中移除，请及时迁移
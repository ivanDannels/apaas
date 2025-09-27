# APaaS Auth 模块 DDD 重构说明

## 重构概述

本模块已根据领域驱动设计（DDD）原则进行重构，以更好地分离关注点并提高代码的可维护性和可扩展性。

## 主要改进

### 1. 引入聚合根模式
- 创建了 `UserAggregate` 类作为用户的聚合根
- 明确标识了聚合根的边界和职责
- 聚合根负责维护内部一致性约束

### 2. 引入值对象模式
- 创建了 `Username` 值对象，用于表示用户名
- 值对象具有不可变性和基于属性的相等性判断

### 3. 明确服务分层
- **应用服务层**：`UserApplicationService` 处理应用层逻辑，协调领域服务和基础设施
- **领域服务层**：`UserDomainService` 处理复杂的业务逻辑和领域规则验证
- **传统服务接口**：`ReactiveUserService` 作为适配器，保持向后兼容性

### 4. 引入工厂模式
- 创建了 `UserFactory` 用于封装复杂对象的创建逻辑
- 确保创建的对象处于一致状态

### 5. 引入规范模式
- 创建了 `UserSpecification` 用于封装可复用的业务规则
- 支持规则的组合（AND、OR、NOT）

### 6. 仓库模式优化
- 使用了 `ReactiveUserRepository` 用于用户实体的持久化

## 包结构说明

```
org.apaas.auth
├── config                  # 配置类
│   └── UserConfig.java                # 用户配置
├── controller              # 控制器层
│   └── reactive
│       └── ReactiveAuthController.java # 认证控制器
├── domain                  # 领域层
│   └── dto
├── entity                  # 实体和聚合根
│   ├── User.java                      # 原始实体（保持兼容性）
│   ├── UserAggregate.java             # 聚合根
│   ├── Username.java                  # 值对象
│   ├── UserRole.java                  # 用户角色实体
│   ├── UserOrganization.java          # 用户组织实体
│   ├── UserPosition.java              # 用户岗位实体
│   └── UserTenant.java                # 用户租户实体
├── factory                 # 工厂类
│   └── UserFactory.java
├── specification           # 规范类
│   └── UserSpecification.java
├── repository              # 仓库层
│   └── reactive
│       └── ReactiveUserRepository.java # 用户仓库接口
├── service                 # 服务层
│   ├── UserApplicationService.java        # 应用服务接口
│   ├── UserDomainService.java             # 领域服务接口
│   └── reactive
│       ├── ReactiveUserService.java       # 传统服务接口（适配器）
│       └── impl
│           ├── ReactiveUserServiceImpl.java     # 传统服务实现（适配器）
│           ├── UserApplicationServiceImpl.java  # 应用服务实现
│           └── UserDomainServiceImpl.java       # 领域服务实现
├── feign                   # Feign客户端
├── form                    # 表单对象
├── utils                   # 工具类
└── AuthApplication.java     # 应用启动类
```

## 使用示例

### 创建用户
```java
// 使用工厂创建聚合根
UserFactory factory = new UserFactory();
UserAggregate user = factory.create(null, "username", "password", "nickname");

// 使用领域服务创建用户
UserDomainService domainService = ...;
Mono<UserAggregate> result = domainService.createUser(user);
```

### 应用服务使用
```java
@RestController
public class UserController {
    
    @Autowired
    private UserApplicationService userApplicationService;
    
    @PostMapping("/users")
    public Mono<User> createUser(@RequestBody User user) {
        return userApplicationService.addUser(user);
    }
}
```

## 向后兼容性

为了保持向后兼容性，我们保留了原有的 `ReactiveUserService` 接口和实现，但将其重构为适配器模式，内部委托给新的应用服务实现。

这样既保持了现有代码的兼容性，又引入了DDD的最佳实践。

## 依赖注入

所有服务都通过Spring的组件扫描自动注册为Bean。工厂类通过配置类手动注册为Bean。
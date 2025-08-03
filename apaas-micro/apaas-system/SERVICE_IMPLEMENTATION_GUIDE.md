# 服务层实现指南

基于对项目中各模块service层实现的分析，我们总结了以下几种实现方式：

## 1. 继承BaseService接口和BaseServiceImpl抽象类

这是最常见的实现方式，能够复用基础的CRUD操作，减少代码重复。

### 示例：NotificationService

```java
public interface NotificationService extends BaseService<Notification, Long> {
    // 自定义业务方法
}

@Service
public class NotificationServiceImpl extends BaseServiceImpl<Notification, Long, NotificationRepository> implements NotificationService {
    // 实现自定义业务方法
}
```

### 优点
- 复用基础CRUD操作
- 代码简洁
- 易于维护

### 适用场景
- 需要基础CRUD操作的实体
- 实体有较多通用操作

## 2. 不继承BaseService接口

服务接口直接定义所需的业务方法，实现类也相应地实现这些方法。

### 示例：SysConfigService

```java
public interface SysConfigService {
    // 直接定义业务方法
}

@Service
public class SysConfigServiceImpl implements SysConfigService {
    // 实现业务方法
}
```

### 优点
- 灵活性高
- 可以完全自定义接口方法

### 适用场景
- 实体操作较为特殊，不需要通用的CRUD操作
- 需要完全自定义的业务逻辑

## 代码生成建议

1. **对于需要基础CRUD操作的实体**：建议其服务接口继承BaseService接口，服务实现类继承BaseServiceImpl抽象类。这样可以复用基础的CRUD操作，提高开发效率。

2. **根据实体特点决定是否注入RedisDomainEventPublisher**：对于需要事件发布的实体，应在服务实现类中注入RedisDomainEventPublisher，以实现事件的发布。

3. **处理特殊业务逻辑**：对于有特殊业务逻辑的实体，如需要状态校验、唯一性校验等，应在服务实现类中添加相应的业务逻辑。

4. **考虑租户隔离**：对于多租户系统，应在服务实现类中考虑租户隔离的实现，如通过TenantContext获取租户ID。

5. **完善分页查询功能**：对于需要分页查询的实体，应完善分页查询的实现，确保分页参数的正确传递和处理。

6. **处理导出和导入功能**：对于需要导出和导入功能的实体，应实现相应的导出和导入方法。

7. **参数校验和异常处理**：在服务实现类中，应添加必要的参数校验和异常处理逻辑，确保系统的稳定性和健壮性。

8. **事务管理**：对于需要事务管理的操作，应使用Spring的事务管理机制，确保数据的一致性。
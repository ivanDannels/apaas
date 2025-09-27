# APaaS Domain 模块设计说明

## 模块概述

`apaas-domain` 模块是基于领域驱动设计（DDD）原则构建的核心领域层，包含了领域模型、领域服务、应用服务、工厂模式和规范模式等核心组件。

## 包结构说明

### 1. entity（实体和值对象）
- `BaseEntity`: 基础实体类，所有实体的基类
- `BaseValueObject`: 值对象基类，所有值对象的基类
- `AggregateRoot`: 聚合根标记接口
- `Money`: 金额值对象示例
- `Order`: 订单聚合根示例
- `OrderItem`: 订单项实体示例

### 2. service（服务层）
- `application` 包：应用服务相关组件
  - `ApplicationService`: 应用服务接口
  - `AbstractApplicationService`: 应用服务抽象实现
  - `BaseService`: 基础服务接口（重新定位为应用服务）
  - `OrderApplicationService`: 订单应用服务示例
  - `impl` 包：
    - `BaseServiceImpl`: 基础服务实现

- `domain` 包：领域服务相关组件
  - `DomainService`: 领域服务接口
  - `AbstractDomainService`: 领域服务抽象实现
  - `OrderDomainService`: 订单领域服务示例
  - `impl` 包：
    - `ExampleDomainService`: 示例领域服务实现

### 3. repository（仓库层）
- `ReactiveBaseRepository`: 响应式基础仓库接口
- `OrderRepository`: 订单仓库接口示例

### 4. factory（工厂模式）
- `EntityFactory`: 实体工厂接口
- `OrderFactory`: 订单工厂实现示例

### 5. specification（规范模式）
- `Specification`: 规范接口
- `OrderSpecification`: 订单规范示例

### 6. exception（异常处理）
- `GlobalExceptionHandler`: 全局异常处理器
- `BusinessException`: 业务异常
- `CacheException`: 缓存异常
- `LockAcquisitionException`: 锁获取异常

## 设计原则

### 1. 值对象（Value Object）
- 值对象是没有唯一标识的对象，通过属性值来判断相等性
- 值对象应该是不可变的
- 提供了 `BaseValueObject` 基类和 `Money` 示例

### 2. 聚合根（Aggregate Root）
- 聚合根是聚合的入口点，负责维护聚合内部的一致性
- 聚合根具有全局唯一标识，是唯一可以被外部直接访问的实体
- 提供了 `AggregateRoot` 标记接口和 `Order` 示例

### 3. 服务分层
- **应用服务**: 处理应用层逻辑，协调领域服务和基础设施层
- **领域服务**: 处理跨实体的复杂业务逻辑，协调多个聚合根的操作

### 4. 工厂模式
- 用于创建复杂的对象，封装对象创建逻辑
- 提供了 `EntityFactory` 接口和 `OrderFactory` 示例

### 5. 规范模式
- 用于封装业务规则，支持规则的组合和复用
- 提供了 `Specification` 接口和 `OrderSpecification` 示例

## 使用示例

### 创建订单
```java
// 使用工厂创建订单
OrderFactory factory = new OrderFactory();
Order order = factory.create(1L, customerId, orderNo);

// 添加订单项
OrderItem item = factory.createOrderItem(orderId, productId, productName, price, quantity);
order.addOrderItem(item);

// 使用领域服务验证并保存订单
OrderDomainService domainService = new OrderDomainService(orderRepository);
Specification<Order> spec = OrderSpecification.nonNegativeAmount()
    .and(OrderSpecification.hasOrderItems())
    .and(OrderSpecification.consistentTotalAmount());

Mono<Order> result = domainService.createOrderWithSpecification(order, spec);
```

### 应用服务使用
```java
@RestController
public class OrderController {
    
    @Autowired
    private OrderApplicationService orderApplicationService;
    
    @PostMapping("/orders")
    public Mono<Order> createOrder(@RequestBody Order order) {
        return orderApplicationService.createOrder(order);
    }
}
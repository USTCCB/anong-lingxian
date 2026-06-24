# 🛒 安农领鲜（后端）

> 校园农产品线上化微信小程序 · Spring Boot 后端

## 简介

「安农领鲜」小程序的后端 API，提供商品展示、库存预扣、订单创建/支付等能力。基于 Spring Boot + MyBatis + Redis 实现，**校级一等奖**项目。

## 技术栈

- Java 17 + Spring Boot 3.2.5
- MyBatis 3.0.3 + H2（可换 MySQL）
- Spring Data Redis（库存预扣 + 下单防重）
- @Transactional 事务管理
- JUnit5 单元测试

## 核心亮点

- ✅ **Redis 库存预扣** + 下单防重（30s TTL）
- ✅ **条件更新扣减库存**（stock >= qty 防超卖）
- ✅ 事务一致性（订单创建 + 库存扣减）
- ✅ 清晰的分层架构
- ✅ 单元测试覆盖下单核心路径

## 快速开始

`ash
mvn spring-boot:run
# 监听 8081
`

## API 速览

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET  | /api/products | 商品列表 |
| GET  | /api/products/{id} | 商品详情 |
| POST | /api/orders | 创建订单（下单） |
| POST | /api/orders/{id}/pay | 支付订单 |

## 示例

`ash
# 下单
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d "{\"userId\":1,\"productId\":1,\"quantity\":2}"

# 支付
curl -X POST http://localhost:8081/api/orders/1/pay
`

## 目录结构

`
src/main/java/com/ustccb/lingxian/
├── AnongLingxianApplication.java
├── controller/    # ProductController / OrderController
├── service/       # 业务层（含事务）
├── mapper/        # MyBatis 映射
└── entity/        # Product / ProductOrder
`

## 关键设计

**为什么用 Redis 做下单防重？**
- 用户短时间内重复点击会触发多次请求
- 30s TTL 内的重复请求直接拒绝，简单有效
- 不阻塞、不占数据库连接

**为什么用条件 UPDATE 扣库存？**
`sql
UPDATE product SET stock = stock - #{qty} WHERE id = #{id} AND stock >= #{qty}
`
- 一次 SQL 解决"判断+扣减"，**避免并发超卖**
- 受影响行数为 0 即视为失败，抛业务异常

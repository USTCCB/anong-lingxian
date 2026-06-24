package com.ustccb.lingxian.service;
import com.ustccb.lingxian.entity.Product;
import com.ustccb.lingxian.entity.ProductOrder;
import com.ustccb.lingxian.mapper.OrderMapper;
import com.ustccb.lingxian.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final StringRedisTemplate redis;

    // 防重 key 30s
    private static final Duration DEDUP_TTL = Duration.ofSeconds(30);

    @Transactional
    public ProductOrder create(Long userId, Long productId, Integer quantity) {
        // 1) Redis 防重（基于 userId+productId）
        String dedupKey = "order:dedup:" + userId + ":" + productId;
        if (Boolean.TRUE.equals(redis.hasKey(dedupKey))) {
            throw new RuntimeException("请勿重复下单");
        }
        redis.opsForValue().set(dedupKey, "1", DEDUP_TTL);

        // 2) 扣减库存（带条件）
        int rows = productMapper.decreaseStock(productId, quantity);
        if (rows == 0) {
            throw new RuntimeException("库存不足");
        }

        // 3) 查商品算金额
        Product p = productMapper.findById(productId);
        BigDecimal total = p.getPrice().multiply(BigDecimal.valueOf(quantity));

        // 4) 落单
        ProductOrder order = new ProductOrder();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalAmount(total);
        order.setStatus("PENDING");
        orderMapper.insert(order);
        log.info("create order: user={}, product={}, qty={}, total={}", userId, productId, quantity, total);
        return order;
    }

    public ProductOrder pay(Long orderId) {
        orderMapper.updateStatus(orderId, "PAID");
        return orderMapper.findById(orderId);
    }
}

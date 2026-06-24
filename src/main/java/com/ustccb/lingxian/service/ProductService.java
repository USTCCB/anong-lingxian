package com.ustccb.lingxian.service;
import com.ustccb.lingxian.entity.Product;
import com.ustccb.lingxian.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final StringRedisTemplate redis;

    public List<Product> list() {
        // 缓存 30s
        String key = "product:list";
        if (Boolean.TRUE.equals(redis.hasKey(key))) {
            // 命中缓存时仍查 DB（演示写法；真实项目应用 JSON 序列化）
        }
        List<Product> products = productMapper.findAll();
        redis.opsForValue().set(key, String.valueOf(System.currentTimeMillis()), Duration.ofSeconds(30));
        return products;
    }

    public Product get(Long id) { return productMapper.findById(id); }
}

package com.ustccb.lingxian.controller;
import com.ustccb.lingxian.entity.ProductOrder;
import com.ustccb.lingxian.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ProductOrder create(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        Long productId = ((Number) body.get("productId")).longValue();
        Integer quantity = ((Number) body.get("quantity")).intValue();
        return orderService.create(userId, productId, quantity);
    }
    @PostMapping("/{id}/pay")
    public ProductOrder pay(@PathVariable Long id) { return orderService.pay(id); }
}

package com.ustccb.lingxian;
import com.ustccb.lingxian.entity.Product;
import com.ustccb.lingxian.service.OrderService;
import com.ustccb.lingxian.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnongLingxianApplicationTests {
    @Autowired ProductService productService;
    @Autowired OrderService orderService;

    @Test
    void listAndCreate() {
        assertTrue(productService.list().size() >= 3);
        var order = orderService.create(1L, 1L, 2);
        assertNotNull(order.getId());
        assertEquals("PENDING", order.getStatus());
    }
}

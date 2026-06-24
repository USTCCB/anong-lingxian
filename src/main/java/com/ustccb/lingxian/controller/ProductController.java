package com.ustccb.lingxian.controller;
import com.ustccb.lingxian.entity.Product;
import com.ustccb.lingxian.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping public List<Product> list() { return productService.list(); }
    @GetMapping("/{id}") public Product get(@PathVariable Long id) { return productService.get(id); }
}

package com.ustccb.lingxian.mapper;
import com.ustccb.lingxian.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
@Mapper
public interface ProductMapper {
    List<Product> findAll();
    Product findById(@Param("id") Long id);
    int decreaseStock(@Param("id") Long id, @Param("qty") int qty);
}

package com.ustccb.lingxian.mapper;
import com.ustccb.lingxian.entity.ProductOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
@Mapper
public interface OrderMapper {
    int insert(ProductOrder order);
    ProductOrder findById(@Param("id") Long id);
    List<ProductOrder> findByUser(@Param("userId") Long userId);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

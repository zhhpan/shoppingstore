package org.spm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.constraints.Max;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.spm.pojo.Product;

import java.util.List;

@Mapper
public interface SearchMapper extends BaseMapper<Product> {

    @Select("select * from store_product.product where match(product_name, product_intro, product_title) against ( #{name} IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION)  ;")
    List<Product> searchByName(String name);
}

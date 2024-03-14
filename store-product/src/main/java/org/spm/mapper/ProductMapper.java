package org.spm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.spm.pojo.Product;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}

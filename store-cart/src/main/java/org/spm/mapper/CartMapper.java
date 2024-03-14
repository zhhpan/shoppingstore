package org.spm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.spm.pojo.Cart;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
}

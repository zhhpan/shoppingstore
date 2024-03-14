package org.spm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.spm.pojo.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}

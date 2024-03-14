package org.spm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.spm.param.CartListParam;
import org.spm.param.OrderParam;
import org.spm.pojo.Order;
import org.spm.utils.R;

public interface OrderService extends IService<Order> {
    R save(OrderParam orderParam);

    R list(CartListParam cartListParam);
}

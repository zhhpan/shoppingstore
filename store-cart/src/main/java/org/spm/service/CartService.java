package org.spm.service;

import org.spm.param.CartSaveParam;
import org.spm.pojo.Cart;
import org.spm.utils.R;

import java.util.List;

public interface CartService {
    R save(CartSaveParam cartSaveParam);

    R list(Integer userId);

    R update(Cart cart);

    R remove(Cart cart);

    R removeList(List<Integer> cartIds);
}

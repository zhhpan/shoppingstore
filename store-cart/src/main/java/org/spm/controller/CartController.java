package org.spm.controller;

import jakarta.validation.Valid;
import org.spm.param.CartListParam;
import org.spm.param.CartSaveParam;
import org.spm.pojo.Cart;
import org.spm.service.CartService;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("save")
    public R save(@RequestBody @Valid CartSaveParam cartSaveParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Param is null");
        }
        return cartService.save(cartSaveParam);
    }

    @PostMapping("list")
    public R list(@RequestBody @Validated CartListParam cartListParam, BindingResult bindingResult){

        if (bindingResult.hasErrors()){

            return R.fail("Shopping cart data query failed!");
        }

        return cartService.list(cartListParam.getUserId());
    }

    @PostMapping("update")
    public R update(@RequestBody @Valid Cart cart, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Param is null");
        }
        return cartService.update(cart);
    }

    @PostMapping("remove")
    public R remove(@RequestBody @Valid Cart cart, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Param is null");
        }
        return cartService.remove(cart);
    }

    @PostMapping("/remove/list")
    public R removeList(@RequestBody List<Integer> cartIds){

        return cartService.removeList(cartIds);
    }
}

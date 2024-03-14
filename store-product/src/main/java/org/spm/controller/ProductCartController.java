package org.spm.controller;

import jakarta.validation.Valid;
import org.spm.param.ProductCollectParam;
import org.spm.param.ProductIdParam;
import org.spm.pojo.Product;
import org.spm.service.ProductService;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductCartController {
    @Autowired
    private ProductService productService;

    @PostMapping("cart/detail")
    public Product detail(@RequestBody @Valid ProductIdParam productIdParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return null;
        }
        R detail = productService.detail(productIdParam);
        Product product = (Product) detail.getData();
        return product;
    }


    @PostMapping("cart/decrease")
    public void decrease(@RequestBody @Valid ProductIdParam productIdParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return;
        }
        productService.decrease(productIdParam);
    }
    @PostMapping("cart/list")
    public List<Product> cartList(@RequestBody @Validated ProductCollectParam productCollectParam, BindingResult result){

        if (result.hasErrors()){
            return new ArrayList<Product>();
        }

        return productService.cartList(productCollectParam);
    }
}

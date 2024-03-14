package org.spm.controller;

import jakarta.validation.Valid;
import org.spm.param.ProductCollectParam;
import org.spm.service.ProductService;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductCollectController {
    @Autowired
    private ProductService productService;

    @PostMapping("collect/list")
    public R productIds(@RequestBody @Valid ProductCollectParam productCollectParam, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return R.ok("No collect products");
        }
        return productService.ids(productCollectParam);
    }
}

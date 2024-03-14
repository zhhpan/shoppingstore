package org.spm.controller;

import org.spm.param.OrderToProductParam;
import org.spm.service.ProductService;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProdectOrderController {

    @Autowired
    private ProductService productService;

    @PostMapping("/order/update")
    public R update(@RequestBody List<OrderToProductParam> orderToProducts){
        return productService.update(orderToProducts);
    }

}

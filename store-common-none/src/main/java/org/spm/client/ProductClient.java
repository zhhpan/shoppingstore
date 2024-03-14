package org.spm.client;

import org.spm.param.OrderToProductParam;
import org.spm.param.ProductCollectParam;
import org.spm.param.ProductIdParam;
import org.spm.pojo.Product;
import org.spm.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "product-service")
public interface ProductClient {

    @PostMapping("/product/collect/list")
    R productIds(@RequestBody ProductCollectParam productCollectParam);

    @PostMapping("/product/cart/detail")
    Product productDetail(@RequestBody ProductIdParam productIdParam);

    @PostMapping("/product/cart/decrease")
    void decrease(@RequestBody ProductIdParam productIdParam);

    @PostMapping("/product/cart/list")
    List<Product> cartList(@RequestBody ProductCollectParam productCollectParam);

    @PostMapping("/product/order/update")
    R update(@RequestBody List<OrderToProductParam> orderToProducts);
}

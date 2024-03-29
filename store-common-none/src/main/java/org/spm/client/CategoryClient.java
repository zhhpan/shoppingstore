package org.spm.client;


import feign.Param;
import org.spm.param.ProductHotsParam;
import org.spm.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("category-service")
public interface CategoryClient {

    @GetMapping("/category/promo/{categoryName}")
    R byName(@PathVariable("categoryName") String categoryName);

    @PostMapping("/category/hots")
    R hots(@RequestBody ProductHotsParam productHotsParam);

    @GetMapping("/category/list")
    R list();
}

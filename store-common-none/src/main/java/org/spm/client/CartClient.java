package org.spm.client;

import org.spm.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "cart-service")
public interface CartClient {

    @PostMapping("/cart/remove/list")
    R removeList(@RequestBody List<Integer> cartIds);
}

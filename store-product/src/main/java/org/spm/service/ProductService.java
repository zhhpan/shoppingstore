package org.spm.service;

import jakarta.validation.Valid;
import org.spm.param.*;
import org.spm.pojo.Product;
import org.spm.utils.R;

import java.util.List;

public interface ProductService {
    R promo(ProductPromoParam productPromoParam);

    R hots(ProductHotsParam productHotsParam);

    R list();

    R bycategory(ProductIdsParam productIdsParam);

    R detail(ProductIdParam productIdParam);

    R ids(ProductCollectParam productCollectParam);

    void decrease(ProductIdParam productIdParam);

    List<Product> cartList(ProductCollectParam productCollectParam);

    R update(List<OrderToProductParam> orderToProducts);
}

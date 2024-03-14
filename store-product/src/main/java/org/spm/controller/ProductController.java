package org.spm.controller;


import jakarta.validation.Valid;
import org.spm.param.ProductHotsParam;
import org.spm.param.ProductIdParam;
import org.spm.param.ProductIdsParam;
import org.spm.param.ProductPromoParam;
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
public class ProductController {

    @Autowired
    private ProductService productService;

    /*
    查询热门商品，最多7个，按照购买量的多少从大到小排序
    地址：/product/promo
    传入json格式数据
     */
    @PostMapping("promo")
    public R promo(@RequestBody @Valid ProductPromoParam productPromoParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("Search failure");
        }
        return productService.promo(productPromoParam);
    }
    /*
    查询热门商品，最多7个，按照购买量的多少从大到小排序
    地址：/product/hots
    传入json格式数据
     */
    @PostMapping("hots")
    public R hots(@RequestBody @Valid ProductHotsParam productHotsParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Search failure");
        }
        return productService.hots(productHotsParam);
    }
    /*
    展示类别列表
    地址：/product/category/list
     */
    @PostMapping("category/list")
    public R list(){
        return productService.list();
    }
    /*
    类别商品信息
    地址：/product/bycategory
     */
    @PostMapping("bycategory")
    public R bycategory(@RequestBody @Valid ProductIdsParam productIdsParam,BindingResult bindingResult){
        if(bindingResult.hasErrors()|| productIdsParam.getCategoryID().isEmpty()){
            return R.fail("Failed to search by category");
        }
        return productService.bycategory(productIdsParam);
    }
    /*
    类别全部商品信息
    地址：/product/all
     */
    @PostMapping("all")
    public R all(@RequestBody @Valid ProductIdsParam productIdsParam,BindingResult bindingResult){
        if(bindingResult.hasErrors()||!productIdsParam.getCategoryID().isEmpty()){
            return R.fail("Failed to search by category");
        }
        return productService.bycategory(productIdsParam);
    }

    /*
    商品详细信息
    地址：/product/detail
     */
    @PostMapping("detail")
    public R detail(@RequestBody @Valid ProductIdParam productIdParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Category details enquiry failed");
        }
        return productService.detail(productIdParam);
    }


}

package org.spm.service;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.spm.client.CategoryClient;
import org.spm.mapper.ProductMapper;
import org.spm.param.*;
import org.spm.pojo.Category;
import org.spm.pojo.Product;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private ProductMapper productMapper;
    /**
     * @param productPromoParam
     * @return
     */
    @Override
    public R promo(ProductPromoParam productPromoParam) {
        //根据类别名称调用feign客户端
        R r = categoryClient.byName(productPromoParam.getCategoryName());
        if(r.getCode().equals(R.FAIL_CODE)){
            return r;
        }
        //成功后，根据id查询数据
        Object Json =r.getData();
        String JsonStr = JSONUtil.toJsonStr(Json);
        Category category = JSONUtil.toBean(JsonStr, Category.class);
        Integer categoryId = category.getCategoryId();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        queryWrapper.orderByDesc("product_sales");//倒序排序
        IPage<Product> page = new Page<>(1,7);//分页
        page = productMapper.selectPage(page,queryWrapper);
        //结果封装
        List<Product> records = page.getRecords();
        return R.ok("Search succssfully",records);
    }

    /**
     * @param productHotsParam
     * @return
     */
    @Override
    public R hots(ProductHotsParam productHotsParam) {
        //根据类别名称调用feign客户端
        R r = categoryClient.hots(productHotsParam);
        if(r.getCode().equals(R.FAIL_CODE)){
            return r;
        }
        //成功后，根据id查询数据
        List<Object> ids = (List<Object>) r.getData();
//        String JsonStr = JSONUtil.toJsonStr(Json);
//        Category category = JSONUtil.toBean(JsonStr, Category.class);
//        Integer categoryId = category.getCategoryId();

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_id",ids);
        queryWrapper.orderByDesc("product_sales");//倒序排序
        IPage<Product> page = new Page<>(1,7);//分页
        page = productMapper.selectPage(page,queryWrapper);
        //结果封装
        List<Product> records = page.getRecords();
        return R.ok("Search succssfully",records);
    }

    /**
     * @return
     */
    @Override
    public R list() {
        R r = categoryClient.list();
        return r;
    }

    /**
     * @param productIdsParam
     * @return
     */
    @Override
    public R bycategory(ProductIdsParam productIdsParam) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if(! productIdsParam.getCategoryID().isEmpty()){
            queryWrapper.in("category_id",productIdsParam.getCategoryID());
        }
        IPage<Product> page = new Page<>(productIdsParam.getCurrentPage(),productIdsParam.getPageSize());//分页
        page = productMapper.selectPage(page,queryWrapper);
        //结果封装
        List<Product> records = page.getRecords();
        if(records.isEmpty()){
            return R.fail("Set is empty");
        }
        return R.ok("Search succssfully",records,page.getTotal());
    }

    /**
     * @param productIdParam
     * @return
     */
    @Override
    public R detail(ProductIdParam productIdParam) {
        Product product = productMapper.selectById(productIdParam.getProductID());
        if(product == null){
            return R.fail("The information corresponding to the productID does not exist");
        }
        return R.ok("search detail success",product);
    }

    /**
     * @param productCollectParam
     * @return
     */
    @Override
    public R ids(ProductCollectParam productCollectParam) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id",productCollectParam.getProductIds());
        List<Product> products = productMapper.selectList(queryWrapper);
        return R.ok("Search collect list successfully",products);
    }

    /**
     *
     */
    @Override
    public void decrease(ProductIdParam productIdParam) {
        Product product = productMapper.selectById(productIdParam.getProductID());
        product.setProductNum(product.getProductNum()-1);
        product.setProductSales(product.getProductSales()+1);
        int i = productMapper.updateById(product);
    }

    @Override
    public List<Product> cartList(ProductCollectParam productCollectParam) {

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id",productCollectParam.getProductIds());

        List<Product> productList = productMapper.selectList(queryWrapper);

        return productList;
    }

    /**
     * @param orderToProducts
     * @return
     */
    @Override
    public R update(List<OrderToProductParam> orderToProducts) {
        orderToProducts.forEach(orderToProductParam -> {
            Integer productId = orderToProductParam.getProductId();
            Integer num = orderToProductParam.getNum();
            Product product = productMapper.selectById(productId);
            product.setProductNum(product.getProductNum()-num);
            int i = productMapper.updateById(product);
        });
        return R.ok("");
    }
}

package org.spm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.spm.client.ProductClient;
import org.spm.mapper.CartMapper;
import org.spm.param.CartSaveParam;
import org.spm.param.ProductCollectParam;
import org.spm.param.ProductIdParam;
import org.spm.pojo.Cart;
import org.spm.pojo.CartVo;
import org.spm.pojo.Product;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductClient productClient;
    /**
     * @param cartSaveParam
     * @return
     */
    @Override
    public R save(CartSaveParam cartSaveParam) {
        //查询商品数据
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.productDetail(productIdParam);
        if(product == null){
            return R.fail("Product has been deleted and cannot be added to cart");
        }
        //检查库存
        if(product.getProductNum() == 0 ){
            R ok = R.ok("Stock is 0 and cannot be purchased");
            ok.setCode("003");
            return ok;
        }
        //库存减少1
        //productClient.decrease(productIdParam);
        //检查是否添加
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartSaveParam.getUserId());
        queryWrapper.eq("product_id",cartSaveParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);
        if(cart != null){
            //购物车存在
            cart.setNum(cart.getNum()+1);
            cartMapper.updateById(cart);
            R ok = R.ok("Add 1 to the number of items in the shopping cart");
            ok.setCode("002");
            return ok;
        }
        //添加购物车
        cart = new Cart();
        cart.setNum(1);
        cart.setUserId(cartSaveParam.getUserId());
        cart.setProductId(cartSaveParam.getProductId());
        int insert = cartMapper.insert(cart);
        //结果封装返回
        CartVo cartVo = new CartVo(product, cart);
        return R.ok("Shopping cart successfully added",cartVo);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
        //用户id查询 购物车数据
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Cart> carts = cartMapper.selectList(queryWrapper);

        //判断是否存在,不存在,返回一个空集合
        if (carts == null || carts.size() ==0){
            carts = new ArrayList<>();//必须返回空数据
            return R.ok("The shopping cart is empty",carts);
        }

        //存在获取商品的id集合,并且调用商品服务查询
        List<Integer> productIds = new ArrayList<>();
        for (Cart cart : carts) {
            productIds.add(cart.getProductId());
        }
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);
        // 商品集合 - 商品map  商品的id = key    商品 = value
        //jdk 8 stream
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //4.进行vo的封装
        List<CartVo> cartVoList = new ArrayList<>();

        for (Cart cart : carts) {
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()), cart);
            cartVoList.add(cartVo);
        }
        R r = R.ok("Database data query successful", cartVoList);
        return r;
    }

    /**
     * @param cart
     * @return
     */
    @Override
    public R update(Cart cart) {
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cart.getProductId());
        Product product = productClient.productDetail(productIdParam);

        //判断下库存
        if (cart.getNum() > product.getProductNum()) {
            return R.fail("Modification failed! Insufficient stock!");
        }

        //修改数据库
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",cart.getUserId());
        queryWrapper.eq("product_id",cart.getProductId());
        Cart newCart =  cartMapper.selectOne(queryWrapper);

        newCart.setNum(cart.getNum());

        int rows = cartMapper.updateById(newCart);

        return R.ok("Modify cart quantity successfully");
    }

    /**
     * @param cart
     * @return
     */
    @Override
    public R remove(Cart cart) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",cart.getUserId());
        queryWrapper.eq("product_id",cart.getProductId());

        int rows = cartMapper.delete(queryWrapper);

        return R.ok("Delete Shopping Cart Data Successfully!");
    }

    /**
     * @param cartIds
     * @return
     */
    @Override
    public R removeList(List<Integer> cartIds) {
        cartIds.forEach(cartId ->{
            int i = cartMapper.deleteById(cartId);
        });
        return R.ok("Remove cart successfully");
    }
}

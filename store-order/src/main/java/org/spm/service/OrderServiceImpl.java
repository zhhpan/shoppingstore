package org.spm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.spm.client.CartClient;
import org.spm.client.ProductClient;
import org.spm.mapper.OrderMapper;
import org.spm.param.CartListParam;
import org.spm.param.OrderParam;
import org.spm.param.OrderToProductParam;
import org.spm.param.ProductCollectParam;
import org.spm.pojo.CartVo;
import org.spm.pojo.Order;
import org.spm.pojo.OrderVo;
import org.spm.pojo.Product;
import org.spm.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService{

    @Autowired
    private CartClient cartClient;

    @Autowired
    private ProductClient productClient;
    /**
     * @param orderParam
     * @return
     */

    @Override
    public R save(OrderParam orderParam) {

        //准备数据
        List<Integer> cartIds = new ArrayList<>();
        List<OrderToProductParam> orderToProducts = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();


        //生成数据
        Integer userId = orderParam.getUserId();
        long orderId = System.currentTimeMillis();

        for (CartVo cartVo : orderParam.getProducts()) {
            cartIds.add(cartVo.getId()); //保存删除的购物车项的id
            OrderToProductParam orderToProduct = new OrderToProductParam();
            orderToProduct.setNum(cartVo.getNum());
            orderToProduct.setProductId(cartVo.getProductID());
            orderToProducts.add(orderToProduct); //商品服务修改的数据

            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderTime(orderId);
            order.setUserId(userId);
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            orderList.add(order);
        }

        //订单数据批量保存
        saveBatch(orderList);

        //发送购物车消息
        R r = cartClient.removeList(cartIds);

        //发送商品服务消息
        productClient.update(orderToProducts);
        return R.ok("Order saved successfully");
    }

    /**
     * @param cartListParam
     * @return
     */
    @Override
    public R list(CartListParam cartListParam) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartListParam.getUserId());
        List<Order> list = list(queryWrapper);

        //分组
        Map<Long, List<Order>> orderMap = list.stream().collect(Collectors.groupingBy(Order::getOrderId));

        //查询商品数据
        List<Integer> productIds = list.stream().map(Order::getProductId).collect(Collectors.toList());

        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);

        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //结果封装
        List<List<OrderVo>> result = new ArrayList<>();

        //遍历订单项集合
        for (List<Order> orders : orderMap.values()) {
            //封装每一个订单
            List<OrderVo> orderVos = new ArrayList<>();
            for (Order order : orders) {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(order,orderVo);
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());
                orderVos.add(orderVo);
            }

            result.add(orderVos);
        }

        R r = R.ok("Order Data Fetch Successful", result);
        return r;
    }
}

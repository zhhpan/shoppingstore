package org.spm.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.spm.client.ProductClient;
import org.spm.mapper.CollectMapper;
import org.spm.param.AddressListParam;
import org.spm.param.ProductCollectParam;
import org.spm.pojo.Collect;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService{

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private ProductClient productClient;
    /**
     * @param collect
     * @return
     */
    @Override
    public R save(Collect collect) {
        //查询是否存在
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",collect.getUserId());
        queryWrapper.eq("product_id",collect.getProductId());
        Long count = collectMapper.selectCount(queryWrapper);
        if(count > 0){
            return R.fail("Already have the collection");
        }
        //不存在添加
        collect.setCollectTime(System.currentTimeMillis());
        int rows = collectMapper.insert(collect);
        return R.ok("Insert Collection Successfully");
    }

    /**
     * @param param
     * @return
     */
    @Override
    public R list(AddressListParam param) {
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("user_id",param.getUserId());
        collectQueryWrapper.select("product_id");
        List<Collect> collects = collectMapper.selectList(collectQueryWrapper);
        ArrayList<Integer> ids = new ArrayList<>();
        collects.forEach(collect -> {
            ids.add(collect.getProductId());
        });
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(ids);
        R r = productClient.productIds(productCollectParam);
        return r;
    }

    /**
     * @param collect
     * @return
     */
    @Override
    public R remove(Collect collect) {
        //查询是否存在
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",collect.getUserId());
        queryWrapper.eq("product_id",collect.getProductId());
        Long count = collectMapper.selectCount(queryWrapper);
        if(count == 0){
            return R.fail("Dont have the collection");
        }
        //存在删除
        int rows = collectMapper.delete(queryWrapper);
        return R.ok("Remove Collection Successfully");
    }
}

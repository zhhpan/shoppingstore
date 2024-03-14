package org.spm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.spm.mapper.CategoryMapper;
import org.spm.param.ProductHotsParam;
import org.spm.pojo.Address;
import org.spm.pojo.Category;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 通过类别名返回包含类别id的R
     * @param categoryName
     * @return
     */
    @Override
    public R byName(String categoryName) {
        //封装参数categoryQueryWrapper
        QueryWrapper<Category> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("category_name",categoryName);
        Category category = null;
        //数据库查询
        try{
            category = categoryMapper.selectOne(QueryWrapper);
        }catch (Exception e){
            return R.fail("Search category failure");
        }


        //结果
        if(category == null){
            return R.fail("Search category failure");
        }
        return R.ok("Search category successfully",category);
    }

    /**
     * 通过类别名集合返回包含类别id集合的R
     * @param productHotsParam
     * @return
     */
    @Override
    public R hotsCategory(ProductHotsParam productHotsParam) {
        //封装参数categoryQueryWrapper
        QueryWrapper<Category> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.in("category_name",productHotsParam.getCategoryName());
        QueryWrapper.select("category_id");
        List<Category> category = null;
        //数据库查询
        try{
            category = categoryMapper.selectObjs(QueryWrapper);
        }catch (Exception e){
            return R.fail("Search categories failure");
        }


        //结果
        if(category.isEmpty()){
            return R.fail("Search categories failure");
        }
        System.out.println(category);
        return R.ok("Search categories successfully",category);
    }

    /**
     * 展示类别集合的R
     * @return
     */
    @Override
    public R list() {
        List<Category> categories = categoryMapper.selectList(null);
        if(categories.isEmpty()){
            return R.fail("The set of category types is empty");
        }
        return R.ok("Successful query for category type collection",categories);
    }
}

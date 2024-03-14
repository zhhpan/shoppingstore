package org.spm.controller;


import jakarta.validation.Valid;
import org.spm.param.ProductHotsParam;
import org.spm.service.CategoryService;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/promo/{categoryName}")
    public R byname(@PathVariable("categoryName") String categoryName){
        if(StringUtils.isEmpty(categoryName)){
            return R.fail("Category name is null");
        }
        return categoryService.byName(categoryName);
    }

    @PostMapping("hots")
    public R hotsCategory(@RequestBody @Valid ProductHotsParam productHotsParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Category collection query failure");
        }
        return categoryService.hotsCategory(productHotsParam);
    }

    @GetMapping("list")
    public R list(){
        return categoryService.list();
    }




}

package org.spm.controller;

import jakarta.validation.Valid;
import org.spm.param.SearchParam;
import org.spm.service.SearchService;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    private SearchService service;
    @PostMapping("search")
    public R search(@RequestBody @Valid SearchParam searchParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Search content is empty");
        }
        return service.search(searchParam);
    }
}

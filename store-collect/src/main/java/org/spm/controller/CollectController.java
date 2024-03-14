package org.spm.controller;


import jakarta.validation.Valid;
import org.spm.param.AddressListParam;
import org.spm.pojo.Collect;
import org.spm.service.CollectService;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("collect")
public class CollectController {

    @Autowired
    private CollectService collectService;
    @PostMapping("save")
    public R save(@RequestBody @Valid Collect collect, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Parameter is empty");
        }
        return collectService.save(collect);
    }

    @PostMapping("list")
    public R list(@RequestBody @Valid AddressListParam param,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Parameter is empty");
        }
        return collectService.list(param);
    }

    @PostMapping("remove")
    public R remove(@RequestBody @Valid Collect collect, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("Parameter is empty");
        }
        return collectService.remove(collect);
    }


}

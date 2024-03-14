package org.spm.controller;

import jakarta.validation.Valid;
import org.spm.param.AddressListParam;
import org.spm.param.AddressRemoveParam;
import org.spm.pojo.Address;
import org.spm.service.AddressService;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /*
    通过user_id查询用户地址
    请求url:"/user/address/list"
    返回结果(json)：
    {
       code:'001',成功;    '004',失败
       msg:'显示信息',
       data: ...
    }
     */
    @PostMapping("/list")
    private R list(@RequestBody @Valid AddressListParam addressListParam, BindingResult bindingResult){
        //查询是否符合校验的规则
        boolean res = bindingResult.hasErrors();
        //不符合校验结果
        if(res){
            return R.fail("Parameter exception, query failure");
        }
        return addressService.list(addressListParam);
    }

    /*
    保存地址信息
    请求url:"/user/address/save"
    返回结果(json)：
    {
       code:'001',成功;    '004',失败
       msg:'显示信息',
       data: ...
    }
     */
    @PostMapping("/save")
    private R save(@RequestBody @Valid Address address, BindingResult bindingResult){
        //查询是否符合校验的规则
        boolean res = bindingResult.hasErrors();
        //不符合校验结果
        if(res){
            return R.fail("Parameter exception, save failure");
        }
        return addressService.save(address);
    }

    /*
    删除地址信息
    请求url:"/user/address/remove"
    返回结果(json)：
    {
       code:'001',成功;    '004',失败
       msg:'显示信息',
    }
     */
    @PostMapping("/remove")
    private R remove(@RequestBody @Valid AddressRemoveParam addressRemoveParam, BindingResult bindingResult){
        //查询是否符合校验的规则
        boolean res = bindingResult.hasErrors();
        //不符合校验结果
        if(res){
            return R.fail("Parameter exception, remove failure");
        }
        return addressService.remove(addressRemoveParam);
    }
}

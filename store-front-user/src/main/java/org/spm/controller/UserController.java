package org.spm.controller;

import org.spm.param.UserCheckParam;
import org.spm.param.UserLoginParam;
import org.spm.param.AddressListParam;
import org.spm.pojo.User;
import org.spm.service.UserService;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    检查账号是否符合规则
    请求url:"/user/check"
    返回结果(json)：
    {
       code:'001',成功;    '004',失败
       msg:'显示信息'
    }
     */
    @PostMapping("/check")
    public R check(@RequestBody @Valid UserCheckParam userCheckParam, BindingResult bindingResult){
        //查询是否符合校验的规则
        boolean res = bindingResult.hasErrors();
        //不符合校验结果
        if(res){
            return R.fail("Account is null, not available");
        }

        return userService.check(userCheckParam);
    }

    /*
    注册账号
    请求url:"/user/register"
    返回结果(json)：
    {
       code:'001',成功;    '004',失败
       msg:'显示信息'
    }
     */
    @PostMapping("/register")
    public R register(@RequestBody @Valid User user, BindingResult bindingResult){
        //查询是否符合校验的规则
        boolean res = bindingResult.hasErrors();
        //不符合校验结果，信息没有填写完整
        if(res){
            return R.fail("Not filled out, not available");
        }
        return userService.register(user);
    }

    /*
    登录账号
    请求url:"/user/login"
    返回结果(json)：
    {
       code:'001',成功;    '004',失败
       msg:'显示信息'
       data:用户信息json数据{user_id=..., userName:..., userPhonenumber:...}
    }
     */

    @PostMapping("/login")
    public R login(@RequestBody @Valid UserLoginParam userLoginParam, BindingResult bindingResult){
        //查询是否符合校验的规则
        boolean res = bindingResult.hasErrors();
        //不符合校验结果，信息没有填写完整
        if(res){
            return R.fail("Not filled out, not available");
        }
        return userService.login(userLoginParam);
    }
}

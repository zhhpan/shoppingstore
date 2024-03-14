package org.spm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.spm.constants.UserConstants;
import org.spm.mapper.UserMapper;
import org.spm.param.UserCheckParam;
import org.spm.param.UserLoginParam;
import org.spm.pojo.User;
import org.spm.utils.MD5Util;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
//    /**
//     * @return
//     */
//    @Override
//    public List<User> list() {
//        return null;
//    }

    /**
     * @param userCheckParam
     * @return
     */
    @Override
    public R check(UserCheckParam userCheckParam) {
        //封装参数userQueryWrapper
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userCheckParam.getUserName());
        //数据库查询
        Long count = userMapper.selectCount(userQueryWrapper);
        if(count == 0){
            return R.ok("Can register");
        }
        return R.fail("Account already exists, unable to register");
    }

    /**
     * @param user
     * @return
     */
    @Override
    public R register(User user) {
        //检查账号是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",user.getUserName());
        Long count = userMapper.selectCount(userQueryWrapper);
        if(count > 0){
            return R.fail("Account already exists, unable to register");
        }
        //密码加密处理 MD5
        String newPassword = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPassword);
        //插入数据库
        int rows = userMapper.insert(user);
        //返回结果
        if(rows == 0) {
            return R.fail("Registration failed, please try again later");
        }
        return R.ok("Registration successfully");
    }

    /**
     * @param userLoginParam
     * @return
     */
    @Override
    public R login(UserLoginParam userLoginParam) {
        //密码加密处理
        String newPassword = MD5Util.encode(userLoginParam.getPassword() + UserConstants.USER_SLAT);
        //数据库查询
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userLoginParam.getUserName());
        userQueryWrapper.eq("password",newPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        //返回结果
        if(user == null){
            return R.fail("Wrong account or password");
        }
        //不返回password
        user.setPassword(null);
        return R.ok("Login Successfully",user);
    }
}

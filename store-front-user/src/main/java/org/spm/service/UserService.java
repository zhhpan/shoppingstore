package org.spm.service;

import org.spm.param.UserCheckParam;
import org.spm.param.UserLoginParam;
import org.spm.pojo.User;
import org.spm.utils.R;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {
   // List<User> list();

    R check(UserCheckParam userCheckParam);

    R register(User user);

    R login(UserLoginParam userLoginParam);
}

package cn.cxnxs.letuschat.controller;

import cn.cxnxs.letuschat.annotation.ResponseResult;
import cn.cxnxs.letuschat.entity.User;
import cn.cxnxs.letuschat.service.UserService;
import cn.cxnxs.letuschat.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @author mengjinyuan
 * @date 2020-10-15 17:03
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseResult
    @RequestMapping("/loginUserInfo")
    public UserInfoVO getLoginUserInfo(String token){
        User loginUser = userService.getLoginUser(token);
        return new UserInfoVO().po2vo(loginUser);
    }
}

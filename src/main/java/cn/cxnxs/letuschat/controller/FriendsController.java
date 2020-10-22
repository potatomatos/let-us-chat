package cn.cxnxs.letuschat.controller;


import cn.cxnxs.letuschat.annotation.ResponseResult;
import cn.cxnxs.letuschat.service.IFriendsService;
import cn.cxnxs.letuschat.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 好友表 前端控制器
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    private IFriendsService friendsService;

    @ResponseResult
    @RequestMapping("/list")
    public List<UserInfoVO> getFriendsList(Integer userId){
        return friendsService.getFriends(userId);
    }
}


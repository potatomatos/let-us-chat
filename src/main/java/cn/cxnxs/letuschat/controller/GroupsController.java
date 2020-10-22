package cn.cxnxs.letuschat.controller;


import cn.cxnxs.letuschat.annotation.ResponseResult;
import cn.cxnxs.letuschat.entity.Groups;
import cn.cxnxs.letuschat.service.IGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 群组 前端控制器
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/groups")
public class GroupsController {

    @Autowired
    private IGroupsService groupsService;

    @ResponseResult
    @RequestMapping("/list")
    public List<Groups> getGroupsListByUserId(Integer userId){
        return groupsService.getGroupsListByUserId(userId);
    }
}


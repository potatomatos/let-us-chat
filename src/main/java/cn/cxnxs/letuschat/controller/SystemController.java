package cn.cxnxs.letuschat.controller;

import cn.cxnxs.letuschat.annotation.ResponseResult;
import cn.cxnxs.letuschat.service.SystemService;
import cn.cxnxs.letuschat.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p></p>
 *
 * @author mengjinyuan
 * @date 2020-10-14 00:15
 **/
@Controller
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @ResponseResult
    @ResponseBody
    @RequestMapping("/system/login")
    public Result<String> login(String username, String password){
        String token=systemService.login(username, password);
        return Result.success("登陆成功",token);
    }
}

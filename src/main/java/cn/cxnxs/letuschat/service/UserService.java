package cn.cxnxs.letuschat.service;

import cn.cxnxs.letuschat.entity.User;

/**
 * @author potatomato
 */
public interface UserService {

    /**
     * 根据token获取登陆的用户信息
     * @param token
     * @return
     */
    User getLoginUser(String token);

}

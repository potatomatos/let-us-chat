package cn.cxnxs.letuschat.service;


/**
 * <p></p>
 *
 * @author mengjinyuan
 * @date 2020-10-13 22:05
 **/

public interface SystemService {

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    String login(String username,String password);

    /**
     * 登出
     * @param token
     */
    void logout(String token);
}

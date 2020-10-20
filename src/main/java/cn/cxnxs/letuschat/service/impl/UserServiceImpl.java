package cn.cxnxs.letuschat.service.impl;

import cn.cxnxs.letuschat.entity.User;
import cn.cxnxs.letuschat.mapper.UserMapper;
import cn.cxnxs.letuschat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p></p>
 *
 * @author mengjinyuan
 * @date 2020-10-15 16:57
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private RedisTemplate<String,User> redisTemplate;

    @Autowired
    private UserMapper userMapper;
    /**
     * 根据token获取登陆的用户信息
     *
     * @param token
     * @return
     */
    @Override
    public User getLoginUser(String token) {
        User user= redisTemplate.opsForValue().get(token);
        if (user!=null){
            return userMapper.selectById(user.getId());
        }
        return null;
    }
}

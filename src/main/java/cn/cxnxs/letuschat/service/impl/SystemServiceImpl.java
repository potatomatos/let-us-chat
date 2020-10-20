package cn.cxnxs.letuschat.service.impl;

import cn.cxnxs.letuschat.entity.User;
import cn.cxnxs.letuschat.exception.LoginException;
import cn.cxnxs.letuschat.mapper.UserMapper;
import cn.cxnxs.letuschat.service.SystemService;
import cn.cxnxs.letuschat.util.PasswordUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 *
 * @author mengjinyuan
 * @date 2020-10-14 00:25
 **/
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    @Transactional
    @Override
    public String login(String username, String password) {

        String salt = PasswordUtil.Salt;
        String secret = PasswordUtil.encrypt(username, password, salt);
        String token= PasswordUtil.encrypt(System.currentTimeMillis()+"", password, salt);
        if (userMapper.selectOne(new QueryWrapper<User>().eq("username",username))==null){
            User userEntity=new User();
            userEntity.setUsername(username);
            userEntity.setPassword(secret);
            userEntity.setToken(token);
            userMapper.insert(userEntity);
            redisTemplate.opsForValue().set(token,userEntity,30, TimeUnit.DAYS);
        }else {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username).eq("password",secret));
            if(user!=null){
                user.setToken(token);
                userMapper.updateById(user);
                redisTemplate.opsForValue().set(token,user,30, TimeUnit.DAYS);
            }else {
                throw new LoginException("密码错误");
            }
        }

        return token;
    }

    /**
     * 登出
     *
     * @param token
     */
    @Override
    public void logout(String token) {

    }
}

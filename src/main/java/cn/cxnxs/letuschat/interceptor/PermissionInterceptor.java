package cn.cxnxs.letuschat.interceptor;

import cn.cxnxs.letuschat.annotation.ResponseResult;
import cn.cxnxs.letuschat.vo.Result;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * <p>权限拦截器</p>
 *
 * @author mengjinyuan
 * @date 2020-10-13 22:29
 **/
@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            String token=request.getParameter("token");
            Result<String> result=null;
            response.setContentType("json");
            response.setCharacterEncoding("utf8");
            if (token!=null){
                Object userInfo=redisTemplate.opsForValue().get(token);
                if (userInfo==null){
                    log.error("======token无效======");
                    result=Result.failure(Result.ResultEnum.TOKEN_EXPIRED,Result.ResultEnum.TOKEN_EXPIRED.getInfo(),null);
                    response.getWriter().write(JSON.toJSONString(result));
                    return false;
                }
            }else {
                log.error("======token非法======");
                result=Result.failure(Result.ResultEnum.ILLEGAL_TOKEN,Result.ResultEnum.ILLEGAL_TOKEN.getInfo(),null);
                response.getWriter().write(JSON.toJSONString(result));
                return false;
            }
        }
        return true;
    }
}

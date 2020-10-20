package cn.cxnxs.letuschat.interceptor;

import cn.cxnxs.letuschat.annotation.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * <p>返回结果拦截器</p>
 *
 * @author mengjinyuan
 * @date 2020-10-13 22:29
 **/
@Slf4j
@Component
public class ResponseResultInterceptor implements HandlerInterceptor {

    public static final String RESPONSE_RESULT_ANN="RESPONSE-RESULT-ANN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod= (HandlerMethod) handler;
            Class<?> clazz = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            if (clazz.isAnnotationPresent(ResponseResult.class)){
                request.setAttribute(RESPONSE_RESULT_ANN,clazz.getAnnotation(ResponseResult.class));
            }else if(method.isAnnotationPresent(ResponseResult.class)){
                request.setAttribute(RESPONSE_RESULT_ANN,method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }
}

package com.zzh.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzh.pojo.Result;
import com.zzh.utils.JwtTokeyUtils;
import com.zzh.utils.ThreadLocalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Map;

/**
 * 登录验证拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private ObjectMapper objectMapper;

    private static final List<String> NO_AUTH_URIS = List.of(
            "/login",
            "/alioss/upload");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.跨域预检OPTIONS请求，放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        //2.非控制方法，放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //3.不需要拦截的方法，放行（登录、上传、...）
        String uri = request.getRequestURI();
        for (String noAuthUri : NO_AUTH_URIS) {
            if (uri.startsWith(noAuthUri)) {
                return true;
            }
        }

        //4.获取token
        String token = request.getHeader("token");

        //5.判断token为空，响应Result对象，消息为NOT_LOGIN，前端会自动跳转到登录页面，返回false
        if (token == null || "".equals(token.trim())) {
            System.out.println("请求资源路径" + uri);
            System.out.println("token令牌为空，请先登录验证！");
            Result result = Result.error("NOT_LOGIN");
            String resultJson = objectMapper.writeValueAsString(result);
            response.getWriter().write(resultJson);
            return false;
        }
        try {
            //6.解析token
            Map<String, Object> map = JwtTokeyUtils.parseJwtToken(token);
            Integer id = Integer.valueOf(map.get("id").toString());
            String username = map.get("username").toString();
            String name = map.get("name").toString();
            System.out.println(id+"::"+username+"::"+name);
            //存到ThreadLoca里
            ThreadLocalUtils.setCurrentId(id);

            //7.都没有问题，放行
            return true;
        } catch (Exception e) {
            //出异常响应Result对象，消息为NOT_LOGIN，前端会自动跳转到登录页面，返回false
            //令牌过期或被篡改
            System.out.println("请求资源路径" + uri);
            System.out.println("token令牌过期或被篡改，请先重新登录验证！");
            Result result = Result.error("NOT_LOGIN");
            String resultJson = objectMapper.writeValueAsString(result);
            response.getWriter().write(resultJson);
            return false;
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.removeCurrentId();
    }
}

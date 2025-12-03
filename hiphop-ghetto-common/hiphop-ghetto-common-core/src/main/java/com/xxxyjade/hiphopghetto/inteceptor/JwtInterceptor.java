package com.xxxyjade.hiphopghetto.inteceptor;

import com.xxxyjade.hiphopghetto.util.JwtUtil;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private static final String tokenName = "authentication";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 非 Controller 方法放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从请求头中获取令牌
        String token = request.getHeader(tokenName);

        // 校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseAuthJwt();
            Long id = Long.valueOf(claims.get("userId").toString());
            log.info("当前用户id:{}", id);
            ThreadUtil.setUserId(id);
            return true;
        } catch (Exception ex) {
            response.setStatus(401);
            return false;
        }
    }

}

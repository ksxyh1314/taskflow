package com.taskflow.taskflow.filter;

import com.taskflow.taskflow.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 第一步：从请求头拿到 Token
        String authHeader = request.getHeader("Authorization");

        // 第二步：判断 Token 格式对不对
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 没有Token，直接放行（让Security自己决定拦不拦）
            filterChain.doFilter(request, response);
            return;
        }

        // 第三步：截取真正的Token（去掉 "Bearer " 前缀）
        String token = authHeader.substring(7);

        // 第四步：解析Token，拿到用户名
        String username = jwtUtil.getUsernameFromToken(token);

        // 第五步：把用户信息存入Security上下文（告诉Spring这个人已经登录了）
        if (username != null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 第六步：放行，继续走后面的流程
        filterChain.doFilter(request, response);
    }
}

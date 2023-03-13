package com.example.UserProfileManager3.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter implements Filter, HandlerInterceptor {
    private static final List<String> EXCLUDED_URLS = Arrays.asList("/user/login", "/api/users/signup/"); //urls that should be excluded to use tokens


    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Check if the request URL is in the excluded URLs list
        if (EXCLUDED_URLS.contains(req.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        String token = req.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String jwt = token.substring(7);

            Claims claims = jwtUtil.validateToken(jwt);//checking if they are valid
            if (claims == null) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            //  adding the authenticated user object to the request object for  use
//            req.setAttribute("userId", claims.getBody().get("id"));
        } catch (JwtException e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(req, res);
    }

}
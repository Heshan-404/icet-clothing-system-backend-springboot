package com.clothify.ecommerce.security.filter;

import com.clothify.ecommerce.dto.user.UserDTO;
import com.clothify.ecommerce.entity.user.UserEntity;
import com.clothify.ecommerce.repository.user.UserDAO;
import com.clothify.ecommerce.security.service.CustomUserDetailsService;
import com.clothify.ecommerce.security.service.JWTService;
import com.clothify.ecommerce.service.user.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserDAO userDAO;
    private final ApplicationContext context;
    private final ObjectMapper mapper;
    private final RoleService roleService;
    private static final String AUTH_TOKEN_NAME = "AUTH_TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromCookies(request);
        try {
            String username = null;
            if (token != null) username = jwtService.extractUserName(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = context.getBean(
                        CustomUserDetailsService.class).loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken nextFilterToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails.getUsername(), null, userDetails.getAuthorities());
                    nextFilterToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(nextFilterToken);
                }
            }
        } catch (SignatureException e) {
            Cookie cookie = new Cookie(AUTH_TOKEN_NAME, "");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(1000 * 60 * 60);
            response.addCookie(cookie);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            return;
        } catch (ExpiredJwtException e) {
            try {
                UserEntity userEntity = userDAO.findById(e.getClaims().getSubject()).orElseThrow();
                String newToken = jwtService.generateToken(mapper.convertValue(userEntity, UserDTO.class));
                Cookie cookie = new Cookie(AUTH_TOKEN_NAME, newToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
                UserDetails userDetails = context.getBean(CustomUserDetailsService.class)
                        .loadUserByUsername(userEntity.getEmail());
                UsernamePasswordAuthenticationToken nextFilterToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(), null, userDetails.getAuthorities());
                nextFilterToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(nextFilterToken);
            } catch (NoSuchElementException exception) {
                Cookie cookie = new Cookie(AUTH_TOKEN_NAME, "");
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    public String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "AUTH_TOKEN".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }


}

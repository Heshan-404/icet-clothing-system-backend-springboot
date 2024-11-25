package com.clothify.ecommerce.security.filter;
import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityDelayFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            long requestDelayMs = 500;
            Thread.sleep(requestDelayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while delaying request", e);
        }
        chain.doFilter(request, response);
    }
}

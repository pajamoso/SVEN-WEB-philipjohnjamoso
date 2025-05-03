package com.pawtastic.petservices.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // For demo: hardcoded token. To replace with dynamic validation later
    private static final String HARDCODED_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImlhdCI6MTY5NTg0ODAwMCwiZXhwIjoxNzAwMDAwMDAwfQ.dummy-signature";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Missing or invalid Authorization header.");
//            return;
//        }
//
//        String token = authHeader.substring(7); // Remove "Bearer "
//
//        if (!HARDCODED_TOKEN.equals(token)) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Invalid token.");
//            return;
//        }

        // Set authentication in the SecurityContext
        // For demo, we are using a hardcoded username and role.
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("user", null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Token is valid, continue with the request
        filterChain.doFilter(request, response);
    }
}

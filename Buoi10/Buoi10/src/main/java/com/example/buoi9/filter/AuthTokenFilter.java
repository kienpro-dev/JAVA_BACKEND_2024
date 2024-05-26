package com.example.buoi9.filter;

import com.example.buoi9.jwt.JwtUtils;
import com.example.buoi9.service.UserDetailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AuthTokenFilter extends OncePerRequestFilter {
    private final static Logger log = LoggerFactory.getLogger(JwtUtils.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);
            if(StringUtils.hasText(token) && jwtUtils.validation(token)) {
                String username = jwtUtils.getUserByToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                SecurityContextHolder.clearContext();
                log.error("Authentication failed!");
            }
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    public String extractToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");
        if(headerToken != null && headerToken.startsWith("Bearer ")) {
            return headerToken.substring(7);
        }
        return null;
    }
}

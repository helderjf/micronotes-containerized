package com.hfaria.micronotesback.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hfaria.micronotesback.authentication.service.JWTService;
import com.hfaria.micronotesback.authentication.service.UserDetailsServiceImpl;

public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        if (!request.getRequestURI().startsWith("/api/auth") 
                && !request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())
                ) {

            String token = getToken(request);

            if (jwtService.validateToken(token)) {
                String subject = jwtService.getSubjectFromJWT(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request, response);

    }

    private String getToken(HttpServletRequest request) {
        String rawToken = request.getHeader("Authorization");
        if (!rawToken.isEmpty() && rawToken.startsWith("Bearer ")) {
            return rawToken.substring(7);
        }
        return rawToken;
    }

}

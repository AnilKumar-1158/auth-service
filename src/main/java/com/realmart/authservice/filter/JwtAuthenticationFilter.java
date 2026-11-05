package com.realmart.authservice.filter;

import com.realmart.authservice.service.CustomUserDetailsService;
import com.realmart.authservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String authToken = request.getHeader("Authorization");

      if(authToken != null && authToken.startsWith("Bearer ")){
              String token = authToken.substring(7);
              if (Strings.isNotEmpty(token)) {
                  try {
                  String subject = jwtService.getSubject(token);
                  SecurityContext context = SecurityContextHolder.getContext();
                  if (context.getAuthentication() == null && jwtService.isValidToken(subject, token)) {
                      UserDetails userDetails = customUserDetailsService.loadUserByUsername(subject);
                      UsernamePasswordAuthenticationToken authenticationToken =
                              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                      context.setAuthentication(authenticationToken);
                  }
              }catch(Exception ex){
                  response.setStatus(401);
                  response.getWriter().write("Invalid token or expired");
                  return;
              }
          }
      }
        filterChain.doFilter(request, response);
    }
}

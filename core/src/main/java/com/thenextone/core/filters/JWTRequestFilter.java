package com.thenextone.core.filters;

import com.google.gson.JsonObject;
import com.thenextone.core.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, ExpiredJwtException, IOException {

        try {
            final String header = httpServletRequest.getHeader("Authorization");
            String jwt = null;
            String userName = null;
            if (header != null && header.startsWith("Bearer")) {
                jwt = header.substring(7);
                userName = jwtUtil.extractUsername(jwt);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
        catch (ExpiredJwtException | SignatureException ex) {
            return403onForbiddenJWT(httpServletRequest, httpServletResponse);
        }
    }

    private void return403onForbiddenJWT(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        JsonObject error403 = new JsonObject();
        error403.addProperty("status", HttpServletResponse.SC_UNAUTHORIZED);
        error403.addProperty("timestamp", LocalDateTime.now().toString());
        error403.addProperty("error", "Bad credentials");
        error403.addProperty("message", "Access denied");
        error403.addProperty("path", httpServletRequest.getServletPath());
        httpServletResponse.getWriter().write(error403.toString());
    }
}

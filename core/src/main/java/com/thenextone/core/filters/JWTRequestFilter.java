package com.thenextone.core.filters;

import com.thenextone.core.exceptions.CoreErrorHandler;
import com.thenextone.core.exceptions.UnAuthorizedException;
import com.thenextone.core.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws UnAuthorizedException, ServletException, IOException {

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
            CoreErrorHandler.writeExceptionToResponse(
                    "Un Authorized fellow",
                    httpServletRequest,
                    httpServletResponse,
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}

package org.codecollad.snapverse.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codecollad.snapverse.services.JwtUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collections;


public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtilityService jwtUtilityService;

    public JwtAuthorizationFilter (JwtUtilityService jwtUtilityService) {}

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        try {
            JWTClaimsSet claims = jwtUtilityService.parseJWT(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (claims.getSubject(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request, response);
    }
}

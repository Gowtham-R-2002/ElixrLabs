package org.medx.elixrlabs.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import org.medx.elixrlabs.service.impl.JwtService;
import org.medx.elixrlabs.service.impl.UserService;

/**
 * <p>
 * JWT Filter for authentication and providing authorization.
 * Uses OncePerRequestFilter for interpreting the request every time HTTP method
 * is called.
 * </p>
 *
 * @author Gowtham R
 */
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    private List<String> whitelistApis = new ArrayList<>(Arrays.asList("/error", "/api-docs", "swagger-ui/**",
            "swagger-resources/**", "initialize","api/v1/auth/login", "api/v1/patients/register"
            , "api/v1/sample-collectors/register", "api/v1/auth/login/verify"));

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            if(request.getRequestURI().contains())
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (userDetails != null && jwtService.isTokenValid(jwt)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

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
import org.medx.elixrlabs.exception.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = null;
        UserDetails userDetails = null;
        try {
            username = jwtService.extractUsername(jwt);
            userDetails = userService.loadUserByUsername(username);
        } catch (JwtException e) {
            handleException(response, "Invalid JWT token", e,  HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            handleException(response, "An error occurred while processing the token", e, HttpServletResponse.SC_UNAUTHORIZED);
        }
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

    private void handleException(HttpServletResponse response, String message, Exception e, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message
                + (e.getCause().getMessage() == null
                ? " "
                : ("Cause : " + e.getCause().getMessage()))
                + "\"}");
        response.getWriter().flush();
    }
}

package org.medx.elixrlabs.service.impl;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.medx.elixrlabs.util.LocationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.medx.elixrlabs.exception.LabException;


/**
 * <p>
 * Service for managing JWT-related operations.
 * This class contains business logic for handling JWT operations, including token generation,
 * extraction, and validation. It acts as a bridge between the controller layer and the security
 * mechanisms for handling JWTs.
 * </p>
 */
@Service
@Getter
@Setter
public class JwtService {

    @Autowired
    private Dotenv dotenv;
    private static String SECRET;
    private static long VALIDITY;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public JwtService() {
        dotenv = Dotenv.load();
        SECRET = dotenv.get("JWT_SECRET_KEY");
        VALIDITY = TimeUnit.MINUTES.toMillis(Long.parseLong(dotenv.get("JWT_EXPIRY_IN_MINUTES")));
    }


    public String generateToken(UserDetails userDetails, LocationEnum place) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        try {
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("roles", roles)
                    .issuedAt(Date.from(Instant.now()))
                    .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                    .signWith(generateKey())
                    .compact();
        } catch (Exception e) {
            logger.warn("Error while generating token: {}", e.getMessage());
            throw new LabException("Error while generating token");
        }
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        try {
            Claims claims = getClaims(jwt);
            return claims.getSubject();
        } catch (Exception e) {
            logger.warn("Error while extracting username from token: {}", e.getMessage());
            throw new LabException("Error while extracting username from token");
        }
    }

    private Claims getClaims(String jwt) throws ExpiredJwtException {
        try {
            return Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            logger.warn("Token has expired: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.warn("Error while parsing token: {}", e.getMessage());
            throw new LabException("Error while parsing token");
        }
    }

    public boolean isTokenValid(String jwt) {
        try {
            Claims claims = getClaims(jwt);
            return claims.getExpiration().after(Date.from(Instant.now()));
        } catch (Exception e) {
            logger.warn("Error while validating token: {}", e.getMessage());
            throw new LabException("Error while validating token");
        }
    }

    public List<String> extractRoles(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.get("roles", List.class);
    }
}

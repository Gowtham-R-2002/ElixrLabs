package org.medx.elixrlabs.service.impl;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.medx.elixrlabs.util.LocationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Service for managing JwtService-related operations.
 * This class contains business logic for handling JwtService operations. It acts as
 * a bridge between the controller layer and the repository layer, ensuring that
 * business rules are applied before interacting with the database.
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
    private String address;

    public JwtService() {
        dotenv = Dotenv.load();
        SECRET = dotenv.get("JWT_SECRET_KEY");
        VALIDITY = TimeUnit.MINUTES.toMillis(Long.parseLong(dotenv.get("JWT_EXPIRY_IN_MINUTES")));
    }


    public String generateToken(UserDetails userDetails, LocationEnum place) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("place", place)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    public LocationEnum extractAddress(String jwt) {
        Claims claims = getClaims(jwt);
        String addressStr = claims.get("address", String.class);
        setAddress(addressStr);
        return LocationEnum.valueOf(addressStr);
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) throws ExpiredJwtException{
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }
}

package org.medx.elixrlabs.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.util.LocationEnum;


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

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private static final String SECRET = "638CBE3A90E0303BF3808F40F95A7F02A24B4B5D029C954CF553F79E9EF1DC0384BE681C249F1223F6B55AA21DC070914834CA22C8DD98E14A872CA010091ACC";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(1440);
    private String address;

    public String generateToken(UserDetails userDetails, LocationEnum place) {
        try {
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("place", place)
                    .issuedAt(Date.from(Instant.now()))
                    .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                    .signWith(generateKey())
                    .compact();
        } catch (Exception e) {
            logger.warn("Error while generating token: {}", e.getMessage());
            throw new LabException("Error while generating token");
        }
    }

    public LocationEnum extractAddress(String jwt) {
        try {
            Claims claims = getClaims(jwt);
            String addressStr = claims.get("address", String.class);
            setAddress(addressStr);
            return LocationEnum.valueOf(addressStr);
        } catch (Exception e) {
            logger.warn("Error while extracting address from token: {}", e.getMessage());
            throw new LabException("Error while extracting address from token");
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
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
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
}

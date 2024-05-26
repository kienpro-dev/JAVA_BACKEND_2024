package com.example.buoi9.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private final static Logger log = LoggerFactory.getLogger(Jwts.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationMs;

    @Value("${jwt.due_refreshToken}")
    private Long dueRefreshToken;

    public String generateTokenByUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshTokenByUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + dueRefreshToken))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUserByToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String getUserByRefreshToken(String refreshToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(refreshToken)
                .getBody().getSubject();
    }

    public boolean validation(String auth) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(auth);
            return true;
        } catch (SignatureException exception) {
            log.error("Invalid JWT signature :{}", exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Invalid JWT malformed :{}", exception.getMessage());
        } catch (ExpiredJwtException exception) {
            log.error("JWT token is expired :{}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("JWT token is unsupported :{} ", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("JWT claims is not empty :{}", exception.getMessage());
        }
        return false;
    }
}


package com.BankManagementSystemProject.security;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; // 5 hours

    private String secret = "mySuperSecretKeyForJwtTokenGeneration1234567890";

    /*//
    Secret Key Issue
    ✅ Better approach:
       * # application.properties
       * jwt.secret=mySuperSecretKeyForJwtTokenGeneration1234567890
       * jwt.expiration=18000
       */
    private Key getSigningKey()

    { return Keys.hmacShaKeyFor(secret.getBytes()); }

    // 🔹 Username
    public String getUsernameFromToken(String token) { return
            getClaimFromToken(token, Claims::getSubject); }

    // 🔹 Expiry
    public Date getExpirationDateFromToken(String token) { return
            getClaimFromToken(token, Claims::getExpiration); }

    // 🔹 Generic claim
    public <T> T getClaimFromToken(String token,
                                   Function<Claims, T> resolver)
    { final Claims claims =
            getAllClaimsFromToken(token); return resolver.apply(claims); }

    // 🔹 All claims
    private Claims getAllClaimsFromToken(String token) { return
            Jwts.parserBuilder() .setSigningKey(getSigningKey()) .build()
                    .parseClaimsJws(token) .getBody(); }

    // 🔹 Expired check
    private Boolean isTokenExpired(String token) { return
            getExpirationDateFromToken(token).before(new Date()); }

    // 🔹 Generate token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); return doGenerateToken(claims,
                userDetails.getUsername()); }

    // 🔹 Internal token creation

    private String doGenerateToken(Map<String,
            Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis() ))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) .compact(); }

    // 🔹Validate token
    public Boolean validateToken(String token, UserDetails
            userDetails) { final String username = getUsernameFromToken(token); return
            (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

 
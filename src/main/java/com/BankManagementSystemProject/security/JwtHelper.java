package com.BankManagementSystemProject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String secret =
            "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    // get username
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // get expiration
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // get claim
    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    // get all claims
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // check expiration
    private Boolean isTokenExpired(String token) {

        final Date expiration =
                getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }

    // generate token
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims =
                new HashMap<>();

        return doGenerateToken(
                claims,
                userDetails.getUsername()
        );
    }

    // create token
    private String doGenerateToken(
            Map<String, Object> claims,
            String subject
    ) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + JWT_TOKEN_VALIDITY * 1000
                        )
                )
                .signWith(
                        io.jsonwebtoken.security.Keys.hmacShaKeyFor(secret.getBytes()),
                        SignatureAlgorithm.HS512
                )
                .compact();
    }

    // validate token
    public Boolean validateToken(
            String token,
            UserDetails userDetails
    ) {

        final String username =
                getUsernameFromToken(token);

        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token)
        );
    }
}
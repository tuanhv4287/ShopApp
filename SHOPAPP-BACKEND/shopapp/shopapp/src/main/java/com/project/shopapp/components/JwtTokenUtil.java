package com.project.shopapp.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secretKey}")
    private String secretKey;
    public String generateToken(com.project.shopapp.models.User user) throws Exception{
        //properties => claims
        Map<String, Object> Claims = new HashMap<>();
//        this.generateSecretKey();
        Claims.put("phoneNumber",user.getPhoneNumber());
        try{
            String token = Jwts.builder()
                    .setClaims(Claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getsignInKey(), SignatureAlgorithm.HS256)
                    .compact();
             return token;
        } catch (Exception e) {
            throw new InvalidPropertiesFormatException("Cannot create jwt token, error:"+ e.getMessage());
        }
    }
    private Key getsignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey); //Decoders.BASE64.decode("OVxOO7htJvgHz3G7HbfpwMi7YpUR03NDiDwwDcC3gTs=")
        return Keys.hmacShaKeyFor(bytes);
    }

    private String generateSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }
    private Claims extractAllClaims(String token){
            return Jwts.parserBuilder()
                    .setSigningKey(getsignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //Check expiration
    private boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhoneNumber(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails){
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}

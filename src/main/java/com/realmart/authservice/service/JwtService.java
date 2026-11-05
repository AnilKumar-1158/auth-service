package com.realmart.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.realmart.authservice.utils.Constants.SECRET_KEY;

@Service
public class JwtService {
    private final SecretKey secretKey;
    JwtService(){
        secretKey = secretKey();
    }

    public String generateToken(String userName, Collection<? extends GrantedAuthority> authorities, String userId){
   List<String> authorityList = authorities.stream().map(GrantedAuthority::getAuthority).toList();
     return Jwts.builder()
                .subject(userName)
                .id(userId)
                .issuer("RealMart Inc!")
                .claim("role", authorityList)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
              .compact();
    }

    private SecretKey secretKey(){
      byte[] secret =  Decoders.BASE64.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(secret);
    }

    private Claims parseToken(String token){
       return Jwts.parser().verifyWith(secretKey)
               .build()
               .parseSignedClaims(token)
               .getPayload();
    }

    public String getSubject(String token){
       return parseToken(token).getSubject();
    }

    private boolean isTokenExpired(String token){
        Date currentDateTime = new Date(System.currentTimeMillis());
        return currentDateTime.after(parseToken(token).getExpiration());
    }

    public boolean isValidToken(String userName, String token){
        return !isTokenExpired(token) && userName.equals(getSubject(token));
    }
}

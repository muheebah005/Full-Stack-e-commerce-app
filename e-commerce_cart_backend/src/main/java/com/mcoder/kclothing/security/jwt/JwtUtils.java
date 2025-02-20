package com.mcoder.kclothing.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.mcoder.kclothing.security.user.AppUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    public String generateTokenForUser(Authentication authentication){
        AppUserDetails userPrincipal = (AppUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority :: getAuthority)
                    .toList();
                return Jwts.builder()
                    .setSubject(userPrincipal.getEmail())
                    .claim("id", userPrincipal.getId())
                    .claim("roles", roles)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + expirationTime))
                    .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

      private Key key(){
          return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
      }

      public String getUsernameFromToken(String token){
          return Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();
      }

      public boolean validateUserToken(String token){
       try {
         Jwts.parserBuilder()
             .setSigningKey(key())
             .build()
             .parseClaimsJws(token);
         return true;
       } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException  | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
       }
      }
}


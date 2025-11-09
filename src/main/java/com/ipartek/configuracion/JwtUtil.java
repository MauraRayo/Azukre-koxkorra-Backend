package com.ipartek.configuracion;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final String SECRET_KEY = "Del_barco_de_Chanquete,No_nos_moveran...";
	
	
	 private Key getSigningKey() {
	        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	    }
	 public String generateToken(String username, String role) {
	        return Jwts.builder()
	                   .setSubject(username)
	                   .claim("role", role.toUpperCase())
	                   .setIssuedAt(new Date())
	                   .setExpiration(new Date(System.currentTimeMillis() + 3600000)) 
	                   .signWith(getSigningKey())
	                   .compact();
	    }

	    public Claims extractClaims(String token) {
	        return Jwts.parserBuilder()
	                   .setSigningKey(getSigningKey())
	                   .build()
	                   .parseClaimsJws(token)
	                   .getBody();
	    }

	    public boolean isTokenValid(String token) {
	        try {
	            Claims claims = extractClaims(token);
	            return claims.getExpiration().after(new Date());
	        } catch (Exception e) {
	            return false;
	        }
	    }

	   
}

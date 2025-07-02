package com.vik.service;

// CLASS To provide service to JWT filter class 

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService 
{
	// Dummy key
	private String secretKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJpQV30";
	
	// Method to generate token
	public String generateToken(String email)
	{
		Map<String, Object> claims = new HashMap<String, Object>();
	
		return Jwts
				.builder()
				.claims()
				.add(claims)
				.subject(email) // Set Token Subject as email
				.issuedAt(new Date(System.currentTimeMillis())) // Issue time current time 
				.expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // Expiration time 30 Minute
				.and()
				.signWith(getKey()) // Set Dummy key
				.compact();
	}

	// Decode dummy key and convert to byte array
	public SecretKey getKey() 
	{
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	
	public String extractEmail(String token) 
	{
		return extractClaims(token, Claims::getSubject);
	}

	public <T> T extractClaims(String token, Function<Claims, T>  claimResolver) 
	{
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) 
	{
		return Jwts.parser()
				.verifyWith(getKey())
				.build().parseSignedClaims(token)
				.getPayload();
	}

	// Validate Token [ Email and Expiration ]
	public boolean validateToken(String token, UserDetails userDetails) 
	{
		final String email = extractEmail(token);
		return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) 
	{
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) 
	{
		return extractClaims(token, Claims::getExpiration);
	}
}

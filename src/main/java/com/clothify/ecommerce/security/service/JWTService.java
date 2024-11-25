package com.clothify.ecommerce.security.service;

import com.clothify.ecommerce.dto.user.UserDTO;
import com.clothify.ecommerce.entity.user.UserEntity;
import com.clothify.ecommerce.repository.user.UserDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {
    private final UserDAO userDAO;

    private String secretKey = "12345678901234567890123456789012657657657567s65765765s7657657fs655";


    public String generateToken(UserDTO user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 0))
                .signWith(getKey())
                .compact();
    }

    public String getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (String) authentication.getPrincipal();
        }
        throw new IllegalStateException("No authenticated user found");
    }

    private Key getKey() {
        byte[] decode = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public UserEntity getUserFromToken(String tokenFromCookies) {
        if (!tokenFromCookies.isEmpty()) {
            return userDAO.findById(extractUserName(tokenFromCookies)).orElseThrow(() ->
                    new UsernameNotFoundException("invalid token"));
        }
        throw new UsernameNotFoundException("invalid token");
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
package com.example.util;

import com.example.dto.LoginDTO;
import com.example.serviceImpl.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    @Autowired
    CustomUserDetailService customUserDetailService;

    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final String secret = "xvajvbamxksalwjetyoldsadsxsksalwjetyoldksalwjetyold";

    long expiryTime = 1200000;//20 Minutes

    public String generateJwt(LoginDTO loginDTO) {
        logger.info("JwtUtil : GenerateJWT");
        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginDTO.getName());
        Claims claims = Jwts.claims()
                .setIssuer(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiryTime));
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String getUsernameFromToken(String token) {
        logger.info("JwtUtil : getUsernameFromToken");
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        logger.info("JwtUtil : getClaimFromToken");
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        logger.info("JwtUtil : getAllClaimsFromToken");
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Date getExpirationDateFromToken(String token) {
        logger.info("JwtUtil :getExpirationDateFromToken");
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        logger.info("JwtUtil : isTokenExpired");
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        logger.info("JwtUtil : validateToken");
        final String name = getUsernameFromToken(token);
        return (name.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
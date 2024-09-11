package com.example.jwt.jwt;

import com.example.jwt.dto.LoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenProvider {

    private static final String SECRET = "vB9vF31TqSmqIUY8cmmOZJBoMWiQzeSbb8FxeeOG8R/+P6QVxu1ojpNUq69mUHzhQbeW9txqz9FHfZ0f3MUrPg==";
    private static final Long VALIDITY = TimeUnit.MINUTES.toMinutes(30 * 24 * 60);

    // generate JWT token
    public String generateToken(LoginDto loginDto) {
        String username = loginDto.getUsername();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + VALIDITY);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    // get username from JWT token
    public String getUsername(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parse(token);
        return true;

    }
}


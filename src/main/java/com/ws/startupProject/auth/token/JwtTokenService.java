package com.ws.startupProject.auth.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.startupProject.auth.dto.Credentials;
import com.ws.startupProject.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@ConditionalOnProperty(name = "hoaxify.token-type", havingValue = "jwt")
public class JwtTokenService implements TokenService {
    SecretKey key = Keys.hmacShaKeyFor(("Fahrettin-Gursoy-Secret-32-chrctr").getBytes());

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public Token createToken(User user, Credentials credentials) {
        TokenSubject tokenSubject = new TokenSubject(user.getId(), user.getActive(), user.getIsAdministrator());
        try {
            String jsonStringSubject = mapper.writeValueAsString(tokenSubject);
            String token = Jwts.builder().setSubject(jsonStringSubject).signWith(key).compact();
//            return new Token("Bearer", token);
            return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }
        var token = authorizationHeader.split(" ")[1];
        JwtParser jwtParser = Jwts.parser().setSigningKey(key).build();
        try {
            Jws<Claims> claims = jwtParser.parseClaimsJws(token);
            var subject = claims.getBody().getSubject();
            var tokenSubject = mapper.readValue(subject, TokenSubject.class);
            User user = new User();
            user.setId(tokenSubject.id());
            user.setActive(tokenSubject.active());
            user.setIsAdministrator(tokenSubject.administrator());
//            return user;
            return null;
        } catch (JwtException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void logout(String authorizationHeader) {

    }

    public static record TokenSubject(Long id, Boolean active, boolean administrator) {
    }
}

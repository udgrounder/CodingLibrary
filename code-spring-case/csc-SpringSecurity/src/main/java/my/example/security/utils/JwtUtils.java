package com.yagaja.papi.openApi.auth.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import my.example.security.exception.JWTException;
import my.example.security.exception.RestApiErrorCode;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtils {



    public static final String TOKEN_TYPE_BEARER = "BEARER";

    private JwtUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getPayload(@NonNull String authHeader, Key key) throws JWTException {

        String token = getToken(authHeader);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .toString()
                ;

    }

    // 토큰 payload 에서 원하는 값 꺼내기
    public static String getPayloadClaim(@NonNull String authHeader, @NonNull String claimName, Key key) throws JWTException {

        String token = getToken(authHeader);

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(claimName, String.class);
        } catch(ExpiredJwtException e) {
            throw  new JWTException(e, RestApiErrorCode.AUTH_TOKEN_EXPIRED_ERROR);
        } catch(UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SecurityException e) {
            throw  new JWTException(e, RestApiErrorCode.AUTH_TOKEN_FORMAT_INVALID_ERROR);
        } catch (Exception e) {
            throw new JWTException(e, RestApiErrorCode.AUTH_TOKEN_ERROR);
        }
    }

    // 토큰 유효성 검사, 유효 기간 확인
    public static void validateToken(@NonNull String authHeader, Key key) throws JWTException {

        String token = getToken(authHeader);

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException e) {
            throw  new JWTException(e, RestApiErrorCode.AUTH_TOKEN_EXPIRED_ERROR);
        } catch(UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SecurityException e) {
            throw  new JWTException(e, RestApiErrorCode.AUTH_TOKEN_FORMAT_INVALID_ERROR);
        } catch (Exception e) {
            throw new JWTException(e, RestApiErrorCode.AUTH_TOKEN_ERROR);
        }
    }


    public static Key getSigninKey(String strKey) {

        byte[] keyBytes = strKey.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    public static String generateJwtToken(String userName, Map<String, Object> calims, Key key, Long expirationMs) {

        Date now = new Date();
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(now)
                .setClaims(calims)
                .setExpiration(new Date(now.getTime() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)

                .compact();
    }


    /**
     * Utils 성 Method
     */

    /**
     * Auth Header String 에서 JWT 문자열만 추출한다.
     * @param authHeader
     * @return
     */
    public static String getToken(String authHeader) throws JWTException {

        String[] tokens = authHeader.split(" ");

        if ( tokens.length != 2 || !TOKEN_TYPE_BEARER.equalsIgnoreCase( tokens[0])) {
            log.error("Token Error - AuthHeader : {}", authHeader);
            throw new JWTException(RestApiErrorCode.AUTH_TOKEN_FORMAT_INVALID_ERROR);
        }

        return tokens[1];
    }


}
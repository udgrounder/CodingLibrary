package my.example.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import my.example.security.model.AuthUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {


//    private static final Algorithm ALGORITHM = Algorithm.HMAC256("asdfasdfasdfasdfwersdfwer");


    @Value("${jwt.token.expirationMs}")
    private int expirationMs;

    @Value("${jwt.token.secretKey}")
    private String secretKey;


    private Key key;

    public static final String TOKEN_TYPE_BEARER = "BEARER";

    public String getPayload(String token) throws RuntimeException {

        if(token == null ) {
            throw new RuntimeException("유효하지 않는 토큰 입니다." );
        }

        String[] tokens = token.split(" ");

        if ( tokens.length != 2 || !TOKEN_TYPE_BEARER.equals( tokens[0].toUpperCase())) {
            throw new RuntimeException("유효하지 않는 토큰 입니다." );
        }

        log.info("only token _{}_" , tokens[1]);

        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey(secretKey))
                .build()
                .parseClaimsJws(tokens[1])
                .getBody()
                .toString()
                ;
    }

    // 토큰 payload 에서 원하는 값 꺼내기
    public static String getPayloadClaim(String token, String claimName) {
        return Jwts.parserBuilder()
//                .setSigningKey(authInfo.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(claimName, String.class);
    }

    // 토큰 유효성 검사, 유효 기간 확인
    public static void validateToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
//                    .setSigningKey(authInfo.getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

        } catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SecurityException | IllegalArgumentException e) {
            log.error(e.getMessage(), e);
//            throw new E("유효하지 않은 인증토큰입니다.");
            throw  e;
        }
    }


    public Key getSigninKey(String strKey) {

        if( key == null ) {
            byte[] keyBytes = strKey.getBytes(StandardCharsets.UTF_8);
            key = Keys.hmacShaKeyFor(keyBytes);
        }

        return key;
    }



//    public Authentication getAuthentication(String accessToken) {
//        // 토큰 복호화
//        Claims claims = parseClaims(accessToken);
//
//        if (claims.get("auth") == null) {
//            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
//        }
//
//        // 클레임에서 권한 정보 가져오기
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get("auth").toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//        // UserDetails 객체를 만들어서 Authentication 리턴
//        UserDetails principal = new User(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
//    }
//
//
//    private Claims parseClaims(String accessToken) {
//        try {
//            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims();
//        }
//    }


    /*


    {
  "auth": "CAMP,AGENCY,CHANNEL",
  "campId": "1234",
  "agencyId": "1234",
  "channelId": "1234",
  "campName": "종이컵캠핑장",
  "iat": 1516239022
}


     */


    public String generateJwtToken(Authentication authentication) {
        AuthUser userPrincipal = (AuthUser) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, getSigninKey(secretKey))
                .compact();
    }


}
package my.example.security.api;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.example.security.utils.JwtUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class RestApiInfo {

    private static final String ENV_OPENAPI_SECRET="openApiSecretKey";


    private Long expirationMs = 3600000L;

    private String secretKey = "adsfasdfasd234234234fasdfasd12312312312312312";


    private Key key;



    @PostConstruct
    public void init() {
        this.key = JwtUtils.getSigninKey(this.secretKey);
    }


}

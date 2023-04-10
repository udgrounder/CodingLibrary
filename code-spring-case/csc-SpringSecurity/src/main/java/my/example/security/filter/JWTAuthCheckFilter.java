package my.example.security.filter;

import lombok.extern.slf4j.Slf4j;
import my.example.security.api.RestApiInfo;
import my.example.security.exception.JWTException;
import my.example.security.exception.RestApiException;
import my.example.security.model.AuthUser;
import my.example.security.service.CustomUserDetailsService;
import my.example.security.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JWTAuthCheckFilter extends BasicAuthenticationFilter {

    private CustomUserDetailsService customUserDetailsService;

    private RestApiInfo restApiInfo;


    /**
     * Creates an instance which will authenticate against the supplied
     * {@code AuthenticationManager} and which will ignore failed authentication attempts,
     * allowing the request to proceed down the filter chain.
     *
     * @param authenticationManager the bean to submit authentication requests to
     */
    public JWTAuthCheckFilter(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, RestApiInfo restApiInfo) {
        super(authenticationManager);
        this.customUserDetailsService = customUserDetailsService;
        this.restApiInfo = restApiInfo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);

        String authHeader = request.getHeader("Authorization");
        log.debug("jwtToken : {}", authHeader);

        // token 이 없으면 인증을 설정 처리 하지 않는다.
        if ( authHeader != null ) {

            // 검증을 위해서 토큰에서 accountId 추출
            String accountId = JwtUtils.getPayloadClaim(authHeader, "CLAIMNAME", restApiInfo.getKey());
            log.info("accountId : {}", accountId);

            // Token validatiaon
            JwtUtils.validateToken(authHeader, restApiInfo.getKey());

            // 사용자 정보설정
            AuthUser authUser;

            try {
                authUser = (AuthUser) customUserDetailsService.loadUserByToken("accountId");
            } catch (RestApiException e) {
                throw new JWTException(e, e.getErrorCode());
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authUser.getAuthUserInfo(), "", authUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        chain.doFilter(request,response);

    }



//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (bearer == null || !bearer.startsWith(JwtUtil.BEARER_TOKEN_PREFIX)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        String token = bearer.substring(JwtUtil.BEARER_TOKEN_PREFIX.length());
//        var result = JwtUtil.verify(token);
//        if (result.isSuccess()) {
//            var member = (LoginInfo.AccountAdaptor) loginService.loadUserByUsername(result.getUsername());
//            var userToken = new UsernamePasswordAuthenticationToken(member.getLoginUser(), null, member.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(userToken);
//            chain.doFilter(request, response);
//        } else {
//            //throw new AuthenticationException("유효하지 않은 토큰입니다.");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//
//            ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_TOKEN);
//            objectMapper.writeValue(response.getWriter(), Res.fail(errorResponse));
//            //response.getOutputStream().write(objectMapper.writeValueAsBytes(Res.fail(errorResponse)));
//        }
//    }



}

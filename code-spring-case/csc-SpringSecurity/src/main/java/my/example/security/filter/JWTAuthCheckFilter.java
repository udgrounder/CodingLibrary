package my.example.security.filter;

import lombok.extern.slf4j.Slf4j;
import my.example.security.model.AuthUser;
import my.example.security.service.CustomUserDetailsService;
import my.example.security.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JWTAuthCheckFilter extends BasicAuthenticationFilter {

    private CustomUserDetailsService customUserDetailsService;
    private JwtUtils jwtUtils;


    /**
     * Creates an instance which will authenticate against the supplied
     * {@code AuthenticationManager} and which will ignore failed authentication attempts,
     * allowing the request to proceed down the filter chain.
     *
     * @param authenticationManager the bean to submit authentication requests to
     */
    public JWTAuthCheckFilter(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils) {
        super(authenticationManager);
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);

        String jwtToken = request.getHeader("Authorization");
        log.info("jwtToken : {}", jwtToken);

        if ( jwtToken != null) {
            String payload = null;
            try {
                payload = jwtUtils.getPayload(jwtToken);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            log.info("payload : {} ", payload);

            AuthUser authUser = (AuthUser) customUserDetailsService.loadUserByUsername("Tester");

//        OpenApiUserDetails openApiUserDetails = new OpenApiUserDetails();

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authUser.getAuthUserInfo(), "", authUser.getAuthorities());

//        SecurityContextHolder.getContext().setAuthentication(token);

//        tring username = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody().getSubject();
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());


//        Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(token);
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

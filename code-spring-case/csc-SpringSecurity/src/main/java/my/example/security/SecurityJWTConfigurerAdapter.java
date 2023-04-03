package my.example.security;

import my.example.security.filter.JWTAuthCheckFilter;
import my.example.security.service.CustomUserDetailsService;
import my.example.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(
        prePostEnabled = true // Spring Security의 @PreAuthorize, @PreFilter /@PostAuthorize, @PostFilter어노테이션 활성화 여부
//        securedEnabled = true // @Secured어노테이션 활성화 여부
//        jsr250Enabled = true   // @RoleAllowed 어노테이션 사용 활성화 여부
)
public class SecurityJWTConfigurerAdapter extends WebSecurityConfigurerAdapter {

    // security filter 를 타지 않는 url 목록을 만들어 사용한다.
    private static final String[] WHITE_LIST = {
            "/readme",
            "/readme2",
            "/error"
    };


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().and()
                .authorizeRequests()
//                .antMatchers(WHITE_LIST).permitAll()
                .mvcMatchers(HttpMethod.GET,WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAt(new JWTAuthCheckFilter(authenticationManagerBean(), customUserDetailsService, jwtUtils), BasicAuthenticationFilter.class);
        http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/api/auth/failed"))
                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/api/access/denied"));


//        http.apply(new JwtSecurityConfig(jwtTokenProvider));
        
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override
//    public void configure(WebSecurity webSecurity) {
//        webSecurity.ignoring().antMatchers(WHITE_LIST);
//    }

}

package my.example.security;

import my.example.security.filter.JWTAuthCheckFilter;
import my.example.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(
//        prePostEnabled = true // Spring Security의 @PreAuthorize, @PreFilter /@PostAuthorize, @PostFilter어노테이션 활성화 여부
        securedEnabled = true // @Secured어노테이션 활성화 여부
//        jsr250Enabled = true   // @RoleAllowed 어노테이션 사용 활성화 여부
)
public class SecurityJWTConfigurerAdapter extends WebSecurityConfigurerAdapter {

//    BasicAuthenticationFilter filter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/", "/**").permitAll()
//                .mvcMatchers(HttpMethod.GET,"/readme").permitAll()
//                .mvcMatchers(HttpMethod.GET,"/error").permitAll()
//                .mvcMatchers(HttpMethod.GET,"/error").permitAll()
//                .antMatchers("/user").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAt(new JWTAuthCheckFilter(authenticationManagerBean(), customUserDetailsService), BasicAuthenticationFilter.class);
        ;

//        http.apply(new JwtSecurityConfig(jwtTokenProvider));
        
    }

    /**
     * Test 용 inmemory user
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("user")).roles("USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("admin")).roles("USER", "ADMIN");
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
}

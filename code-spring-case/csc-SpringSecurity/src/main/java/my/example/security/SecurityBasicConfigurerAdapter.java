package my.example.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * http header 에 Authorization basic 방식으로 로그인을 관리 한다.
 * id:paw 는 base64로 인코딩 해야 한다.
 * Authorization: Basic id:pwd
 */
//@Configuration
//@EnableWebSecurity(debug = true)
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true, // Spring Security의 @PreAuthorize, @PreFilter /@PostAuthorize, @PostFilter어노테이션 활성화 여부
//        securedEnabled = true, // @Secured어노테이션 활성화 여부
//        jsr250Enabled = true   // @RoleAllowed 어노테이션 사용 활성화 여부
//)
public class SecurityBasicConfigurerAdapter // extends WebSecurityConfigurerAdapter
{

//    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/readme").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
        ;
        
    }

//    /**
//     * Test 용 inmemory user
//     * @param auth the {@link AuthenticationManagerBuilder} to use
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("user")).roles("USER")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("admin")).roles("USER", "ADMIN");
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//


}

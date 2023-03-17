package my.example.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity(debug = true)
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true, // Spring Security의 @PreAuthorize, @PreFilter /@PostAuthorize, @PostFilter어노테이션 활성화 여부
//        securedEnabled = true, // @Secured어노테이션 활성화 여부
//        jsr250Enabled = true   // @RoleAllowed 어노테이션 사용 활성화 여부
//)
public class SecurityFormLoginConfigurerAdapter // extends WebSecurityConfigurerAdapter
{

//    @Autowired
//    MemberService memberService;
//    @Autowired
//    UnauthorizedAuthenticationEntryPoint unauthorizedAuthenticationEntryPoint;

//    @Value("${server.servlet.session.cookie.name}")
//    private String sessionCookieName;


//    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin()
                .defaultSuccessUrl("/user")
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/readme").permitAll()
                .anyRequest().authenticated()
        ;

    }

    /**
     * Test 용 inmemory user
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("user")).roles("USER")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("admin")).roles("USER", "ADMIN");
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }



}

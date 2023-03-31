# csc-SpringRestDocs (작성중)


Rest API 문서 자동화

Spring RestDocs -> OpenAPI 문서 -> Swegger or stoplight 에서 사용

## 작업 중 



## Spring Rest Dosc


### 참조 링크
[우아한 형제들 기술 블로그] <https://techblog.woowahan.com/2597>  
[참조] <https://helloworld.kurly.com/blog/spring-rest-docs-guide>
[참조] <https://springboot.tistory.com/26>
[참조] <https://jaehun2841.github.io/2019/08/04/2019-08-04-spring-rest-docs/#Spring-Rest-Docs-Architecture>



## Ascii docs

### 참조 링크

[공식 페이지] <https://asciidoc.org>  
[문서] <https://gnidinger.tistory.com/524>  
[문서] <https://narusas.github.io/2018/03/21/Asciidoc-basic.html>
[문서] <https://dotheright.tistory.com/339>


## Spring security 를 사용 하는 경우 추가 작업 

Spring security를 사용하는 경우 단위 테스트에서는 콘트롤러와 직접 연관된 환경들에 대해서는 주입 하지 않기 때문에 Security 에서 사용하는 것을 수동으로 설정하고 주입해 줘야 한다. 
우선 Security 관련 Componet를 스캔해서 bean 객체를 만들어야 한다.
그리고 setUpRestdocs() 메소드 처럼 인증 객체를 직업 생성하고 인증을 주입해 줘야만 @AuthenticationPrincipal 어노테이션이 동작을 한다. 

```
@ComponentScan ( basePackages = {"com.yagaja.papi.openApi","com.yagaja.papi.security"})
@WebMvcTest(controllers = {CampOpenApiController.class})
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("dev")
class CampOpenApiControllerTest {
    
    // ....
    
    
    @BeforeEach
    public void setUpRestdocs(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(uriModifyingOperationPreprocessor(), prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        
        // 인증 객체 주입 작업
        AuthUser authUser = new AuthUser(AuthUser.Info.builder()
                .username("3307")
                .campId(3307L)
                .password("")
                .customersType(AuthUser.CustomersType.CAMP)
                .build());

        // 인증 객체를 생성합니다.
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUser.getAuthUserInfo(), null, authUser.getAuthorities());
        // SecurityContext 객체를 생성하고, 인증 객체를 설정합니다.
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        // SecurityContextHolder에 SecurityContext 객체를 설정합니다.
        SecurityContextHolder.setContext(securityContext);
        }
    // ...
}
 
```



### Security 와 관련 업는 테스트에서 security 관련 사항 제외 하기
그리고 만약 같은 프로젝트에서 Security를 사용하지 않는 경우 excludeFilters 설정을 이용해 Security 관련 설정을 제외 하도록 추가 해야 한다.

```
@WebMvcTest(controllers = {VreviewApiController.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)}) 
        @ExtendWith(RestDocumentationExtension.class)
public class VreviewApiControllerTest {
    // ....
}
```

### application.yml 파일 사용 하기

* Active profile 을 설정하지 않아서 @Value 가 값을 못읽어 오는 경우 설정 방법
```
    @ActiveProfiles("dev")
```

* 테스트 시에 property 값을 변경하고 싶은 경우 아래와 같이 Test 클래스 상단에 값을 설정 한다.
``` 
@TestPropertySource(properties = {"token.expiration=7000"})
```


### 참조 링크
[참조 링크 - Test 케이스에서 Security 오류 케이스](https://velog.io/@cieroyou/WebMvcTest%EC%99%80-Spring-Security-%ED%95%A8%EA%BB%98-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0)


## 참고 사항

RestApi 가 Get 방식인 경우에 쿼리 파라메터의 타입을 설정하는 메소드가 없다. 
이유는 Get 방식의 파라메터는 모두 String 방식으로 전달되기 때문에 String 외에 타입이 있을 수가 없어서 설정이 안된다. 
하지만 파라메터를 받는 쪽에서는 프레임워크에서 제공되는 방식으로 객체에 담아서 받기 때문에 문서에 표형이 되어야 한다. 
그래서 다음과 같이 attribute를 추가 하는 식으로 처리가 가능하다. 

```
    parameterWithName("phoneNumber")
        .description("전화번호")
        .attributes(key("type").value("String"))
        .attributes(key("constraints").value(descriptionsForNameProperty(BookingRequest.class, "phoneNumber"))),
```



[SpringBoot 테스트 시 @WebMvcTest와 @SpringBootTest의 차이](https://ksh-coding.tistory.com/53)  

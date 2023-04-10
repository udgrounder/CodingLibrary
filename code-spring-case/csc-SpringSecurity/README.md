# csc-SpringSecurity

## 작업 중 
spring security 를 사용 하는 프로젝트 샘플



[JWT SAMPLE ] <https://www.toptal.com/spring/spring-security-tutorial>  
[참고] <https://hou27.tistory.com/entry/Spring-Security-JWT>  
[참고] <https://devjem.tistory.com/72>  
[참고] <https://codediary21.tistory.com/m/95>  
[나의 스프링 시큐리티/JWT](https://velog.io/@backfox/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0JWT-%EC%9E%85%EB%AC%B8%EC%9E%90%EB%A5%BC-%EC%9C%84%ED%95%9C-%EB%8F%85%ED%95%99-%EC%BB%A4%EB%A6%AC%ED%81%98%EB%9F%BC-%EC%B6%94%EC%B2%9C-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-%EB%8F%99%EC%9E%91%EC%9B%90%EB%A6%AC-PDF-%EA%B3%B5%EC%9C%A0)     
[JWT 생성하기](https://erjuer.tistory.com/87)  
[Spring Security의 Unauthorized, Forbidden 처리](https://velog.io/@park2348190/Spring-Security%EC%9D%98-Unauthorized-Forbidden-%EC%B2%98%EB%A6%AC)   
[ 스프링 시큐리티 + JWT 예외처리하기 및 고찰](https://velog.io/@jkijki12/JWT-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-JWT-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0-%EB%B0%8F-%EA%B3%A0%EC%B0%B0)  
[HTTP 상태 401(Unauthorized) vs 403(Forbidden) 차이](https://mangkyu.tistory.com/146)   







### FORM Login 
* 기본 제공 FormPage
  * login
  * logout
  


SecurityConfigurerAdapter 파일에 다음 설정만 하면 된다.
```
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
    }
```

Stdout 에  기본 계정 패스워드 제공



### 주의 사항

``` 
.mvcMatchers(HttpMethod.GET,WHITE_LIST).permitAll()
```

security config 에서 위의 문법을 사용해도 필터를 동일하게 타게 된다. 
따라서 필터가 적용 되기 때문에 이 방식으로는 우회해 갈수가 없다.
그래서 아래와 같은 방법으로 필터 자체를 안타게 해도 된다 
```

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(WHITE_LIST);
    }
```

또는 해당 필터 에서 token 이 없는 경우 설정을 하지 않도록 처리하는 것도 방법이다. 
이 방법이 플로우상 맞아 보인다.





[인증인가 에러 처리](https://fenderist.tistory.com/344)  

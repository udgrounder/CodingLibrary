# csc-SpringSecurity

## 작업 중 
spring security 를 사용 하는 프로젝트 샘플



[JWT SAMPLE ] <https://www.toptal.com/spring/spring-security-tutorial>  
[참고] <https://hou27.tistory.com/entry/Spring-Security-JWT>  
[참고] <https://devjem.tistory.com/72>  
[참고] <https://codediary21.tistory.com/m/95>  
[참고] <https://velog.io/@backfox/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0JWT-%EC%9E%85%EB%AC%B8%EC%9E%90%EB%A5%BC-%EC%9C%84%ED%95%9C-%EB%8F%85%ED%95%99-%EC%BB%A4%EB%A6%AC%ED%81%98%EB%9F%BC-%EC%B6%94%EC%B2%9C-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-%EB%8F%99%EC%9E%91%EC%9B%90%EB%A6%AC-PDF-%EA%B3%B5%EC%9C%A0>  
[JWT 생성하기] <https://erjuer.tistory.com/87>







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




# csc-SpringSecurity

## 작업 중 
spring security 를 사용 하는 프로젝트 샘플



[JWT SAMPLE ] <https://www.toptal.com/spring/spring-security-tutorial>  
[참고] <https://hou27.tistory.com/entry/Spring-Security-JWT>  
[참고] <https://devjem.tistory.com/72>  
[참고] <https://codediary21.tistory.com/m/95>  







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


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


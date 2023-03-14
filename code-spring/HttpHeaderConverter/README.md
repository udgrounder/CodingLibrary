# HttpHeaderConverter

HTTP 헤더에 담은 값을 Controller 파라메터 변수로 변환해서 담는 예제 이다.

헤더의 x-auth-user 에 있는 json 값을 XAuthUser 객체로 옮겨 담는다.

XAuthUserConverter Bean 으로 등록하면 자동으로 Controller 에서 맞는 타입으로 현변환을 시켜준다. 

Controller 에서 다음과 같이 받아서 사용하면 된다.

```
public ResponseEntity getUseInfo(@RequestHeader("x-auth-user") XAuthUser xAuthUser) {
    ...
}
```

String 내에 내장된 ConversionService 라는 내장된 서비스에서 Converter 구현체 Bean들을 Converter 리스트에 등록한다. 

외부 데이터가 들어오고, Source Class Type -> Target Class Type 이 Converter 에 등록된 형식과 일치 하면 해당 Converter 가 동작한다.


## Test
intelliJ 에서 제공하는 http 파일로 테스트 가능한다. 
/src/test/Scratches/scratch.http



## 참고
포맷을 변경해 주는 Formatter 도 있다.
날짜 포맷을 통일할때 유용해 보인다. 

날짜 포맷 관련 아래 참조

https://www.ionos.com/digitalguide/websites/web-development/iso-8601/

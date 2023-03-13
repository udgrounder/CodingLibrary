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

String 내에 내장된 ConversionService 라는 내장된 서비스에서 Converter 구현체 Bean들을 Converter fltmxmdp emdfhr gksek. 

외부 데이터가 들어오고, Souvce Class Type -> Target Class Type 이 Converter에 등록된 형식과 일치 하면 해당 Converter 가 동작한다.


## Test
intelliJ 에서 제공하는 http 파일로 테스트 가능한다. 
/src/test/Scratches/scratch.http
# VALIDATION

@Valid와 @Validated의 가장 큰 차이점은 검증 항목을 그룹으로 나눠서 검증할 수 있는지, 즉 Group Validation이 가능한 지 여부다. 자바에서 객체를 검증할 때 사용하도록 구현된 것이 javax.validation의 @Valid라면 여기서 Group Validation까지 가능하도록 구현된 것이 스프링 프레임워크의 @Validated라고 할 수 있다. 전자는 JSR-303 스펙을 따르고 후자는 그렇지 않지만 스프링 프레임워크에서는 둘 다 혼용해서 사용할 수 있다.


개발시 Group Validation 을 사용하지 않으면 처리가 어려운 경우가 많아서 기본으로 사용 하기로 한다.

Group Validation 은 인터페이스를 이용해서 그룹을 묶는다. 
그리고 인터페이스의 상속을 이용 해서 공통 처리가 가능 하다.

### 처리 사항

* 라이브러리 의존성 추가
  * implementation 'org.springframework.boot:spring-boot-starter-validation'
* 모델에 validation 설정
```java

public class MyAccount {
    
    public interface ValidMyAccount {}
	public interface ValidAddAccount extends ValidMyAccount {}
	public interface ValidUpdateAccount extends ValidMyAccount {}


	@NotNull(message = "계좌가 선택 되지 않았습니다. 계좌를 선택해 주세요.",
			groups = {ValidUpdateAccount.class})
	Long			campBankAccountId;	// 캠핑장무통장 계좌 정보 아이디

	@NotBlank(message = "캠핑장 아이디를 입력해 주세요.",
			groups = { ValidMyAccount.class})
	Long			campId;				// 캠핑장아이디

	@NotBlank(message = "은행코드를 입력해 주세요.",
			groups = { ValidMyAccount.class})
	String			bankCode;			// 은행코드

	@NotBlank(message = "계좌번호를 입력해 주세요.",
			groups = { ValidMyAccount.class})
	String bankAccount;		// 무통장계좌번호
}


```

* 사용할 서비스에 설정
  * Class 에 어노테이션 설정
  * 사용할 메소드에 어노테이션 설정
```java
@Validated // <-- Class annotaion 설정
@RequiredArgsConstructor
public class BankAccountService {
    // <-- 메소드 정의 부분에 설정 2곳 모두 annotation 설정
    @Validated(MyAccount.ValidUpdateAccount.class)
    public MyAccount updateMyAccount(@Valid MyAccount myAccount) {
        // ...
    }

}
```




### 참고 사항 
 * HV000030: No validator could be found for constraint 'javax.validation.constraints.NotBlank' validating type 'java.lang.Long'. Check configuration for XXX
   * 이 에러는 Long 형 타입은 NotBlank를 쓸수 없다는 오류이다. 숫자형이라 Null 이 아니면 숫자가 존재 하기 때문에 사용이 불가하다.
 * @Size 는 배열의 사이즈를 체크 하는데 사용한다.
   * Integer 의 숫자 범위 체크 하는데 사용 안된다.

#### 동작 하지 않는 케이스

여러가지 조합을 시도 했으나 동작 하지 않거나 에러가 난다. 
* case 1
  * 메소드 정의에만 넣은 케이스
```java
public MyAccount updateMyAccount(@Validated(MyAccount.ValidUpdateAccount.class) MyAccount myAccount){
        
        }
```

* case 2
  * @Valid 를 뺀 케이스
```java
@Validated(MyAccount.ValidUpdateAccount.class)
public MyAccount updateMyAccount(MyAccount myAccount){
        
        }
```

* case 3
    * 메소드 정의에 Groups 설정 하려는 케이스 - 이건 그냥 에러가 난다.
```java

public MyAccount updateMyAccount(@Validated(groups={MyAccount.ValidUpdateAccount.class})MyAccount myAccount){
        
        }
```


## 에러 처리 

Controller 또는 Service에서 에러를 발생 시키고 Advice 에서 공통으로 에러를 처리 한다. 

```java

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandlerAdvice {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public RestResponse handleConstraintViolationException(ConstraintViolationException e) {
		log.debug( "handleException.ConstraintViolationException" );
		log.debug( e.toString() );
		RestResponse restResponse = new RestResponse();
		restResponse.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR);
		String message = "입력값을 확인해 주시기 바랍니다.";
		if ( !e.getConstraintViolations().isEmpty()) {
			Optional<ConstraintViolation<?>> violation = e.getConstraintViolations().stream().findFirst();
			if ( violation.isPresent() ) {
				message = violation.get().getMessage();
			}
		}
		restResponse.setResultMessage(message);

		return restResponse;
	}
}

```



### 참조 링크
* [참조 링크 1](https://reflectoring.io/bean-validation-with-spring-boot/)  
* [참조 링크 2](https://reflectoring.io/bean-validation-anti-patterns/#anti-pattern-3-using-validation-groups-for-use-case-validations)  
* [Spring의 @Valid, @Validated](https://velog.io/@park2348190/Spring%EC%9D%98-Valid-Validated)  
* [Validator 에러 케이스 ](https://sas-study.tistory.com/473)  

# Annatation

### Annotation 이란?
Annotation 자체는 주석과도 같다. 즉 코드 사이에 주석처럼 쓰이면서 특별한 의미 기능을 수행하도록 하는 기술로 프로그램에게 추가적인 정보르 ㄹ제공해주는 메타 데이터 이다. 


### Annotation의 용도 
* 컴파일러에게 코드 작성 문법 에러를 체크 하도록 정보를 제공한다. 
* 소프트웨어 개발툴이 빌드나 배치 시 코드를 자동으로 생성할 수 있도록 정보를 제공한다. 
* 런타임시 특정 기능을 실행 하도록 정보를 제공한다. 

Spring 에서 제공되는 대부분의 Annotation은 3번, 런타임 시 특정 기능을 실행하도록 정보를 제공하는 용도로 사용되고 있다. 

### Annotation 파일 정의 하기 
```java
@Target({ElementType.[적용대상]})
@Retention(RetentionPolicy.[정보유지되는 대상])
public @interface [어노테이션 이름]{
    ...
}
```
1) Target(어노테이션의 적용대상)
* ANNOTATION_TYPE : Annotation type declaration : 어노테이션 타입 선언
* CONSTRUCTOR : Constructor declaration : 생성자 선언
* FIELD : Field declaration (includes enum constants) : 필드 선언
* LOCAL_VARIABLE : Local variable declaration : 지역 변수 선언
* METHOD : Method declaration : 메서드 선언
* PACKAGE : Package declaration : 패키지 선언
* PARAMETER : Formal parameter declaration : 파라메터 선언
* TYPE : Class, interface (including annotation type), or enum declaration : 타입 선언
* TYPE_PARAMETER : Type parameter declaration : 타입 파라메터 선언
* TYPE_USE : Use of a type : 타입 사용 선언 - 타입이 사용되는곳 선언


2) Retention (어노테이션이 유지되는 대상)
* CLASS : Annotations are to be recorded in the class file by the compiler but need not be retained by the VM at run time. : 컴파일러에 의해 클래스 파일에 기록되지만 런타임에는 유지되지 않는다.
* RUNTIME : Annotations are to be recorded in the class file by the compiler and retained by the VM at run time, so they may be read reflectively. : 컴파일러에 의해 클래스 파일에 기록되고 런타임에 유지된다.
* SOURCE : Annotations are to be discarded by the compiler. : 소스에만 반영되어 컴파일러에 의해 삭제된다.


3) Annotation Name(Class 가 아닌 @interface로 정의된 어노테이션 이름)


### 샘플 분석 - Spring @Controller Annotation 분석

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";

}
```
* @Target({ElementType.TYPE})
  * 타입 선언 부분에 사용 가능 하다는 것을 알려 준다.
* @Retention(RetentionPolicy.RUNTIME)
  * 컴파일 되고 runtime 시까지 유지된다. 
* @AliasFor(annotation = Component.class)
  * 


### @aliasFor annotation

@AliasFor 어노테이션에서 @AliasFor를 사용하는 것은 문제가 되지 않는 이유는 Java 언어에서 어노테이션 처리 방식과 관련이 있습니다.  
Java 언어에서 어노테이션은 컴파일 타임에 처리되는 메타데이터(meta-data)로, 런타임 시점에서는 어노테이션 정보에 접근할 수 없습니다.  
따라서, 컴파일러는 어노테이션의 선언을 확인할 때, 해당 어노테이션의 정보를 먼저 메모리에 로드한 뒤에 처리합니다.  
그리고, Java 언어에서는 어노테이션 선언 시 순환 참조(circular reference)를 방지하기 위해 어노테이션의 값(value)이나 다른 속성(attribute)에 대한 참조를 컴파일 타임에 확인하도록 정해져 있습니다. 
따라서, @AliasFor 어노테이션에서도 @AliasFor를 사용해도 순환 참조가 발생하지 않습니다.
예를 들어, 다음과 같이 @AliasFor 어노테이션에서 attribute 속성과 value 속성이 서로를 참조하는 경우를 생각해보겠습니다.

ChatGPT에게 한참을 이것 저것 물어본 결과 
일단 aliasFor 는 값을 일치 시켜준다는 이야기지 변수를 하나로 만들어 준다는 이야기는 아닌것으로 보인다. 
이를테면 value 에 설정하면 path에도 동일하게 값을 설정하게 동작히고 그 역으로도 한다는 것으로 이해 된다. 

그리고 컴파일시에는 정보를 확인하지 않고 런타임시에 값을 서로 넣는 부분만 동작하기 때문에 문제가 없다는것으로 이해 된다. 

### 참고
* [Annotation 동작원리와 사용법](https://hirlawldo.tistory.com/43)
* [Target enum](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/ElementType.html)
* [RetentionPolicy enum](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html)
* [@Controller, @Service 차이](https://hi-dot.tistory.com/20)
* [aliasfor annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/annotation/AliasFor.html#attribute--)
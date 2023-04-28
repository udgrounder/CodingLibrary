# Transaction 처리

트랜젝션은 데이터베이스의 상태를 변경시키는 작업 또는 한번에 수행되어야하는 연산들을 의미한다. 
트랜젝션 작업이 끝나면 Commit 또는 Rollback 이 되어야 한다. 

### 트랜잭션의 성질
* 원자성(Atomicity)
  * 한 트랜잭션 내에서 실행한 작업들은 하나의 단위로 처리합니다 즉, 모두 성공 또는 모두 실패 
* 일관성(Consistency)
  * 트랜잭션은 일관성 있는 데이터베이스 상태를 유지한다. 
* 격리성(Isolation)
  * 동시에 실행되는 트랜잭션들이 서로 영향을 미치지 않도록 격리해야한다. 
* 영속성(Durability)
  * 트랜잭션을 성공적으로 처리되면 결과가 항상 저장되어야한다.


String 에서는 선언적 트랜젝션을 어노테이션으로 제공한다.  
```
@Transactional
```

이 어노테이션을 살펴 보면 설정 값을 확인 할 수 있다. 

### @Transactional 옵션
* propagation
  * 트랜잭션 동작 도중 다른 트랜잭션을 호출할 때, 어떻게 할 것인지 지정하는 옵션이다
* isolation
  * 트랜잭션에서 일관성없는 데이터 허용 수준을 설정한다
* noRollbackFor=Exception.class
  * 특정 예외 발생 시 rollback하지 않는다.
* rollbackFor=[Exception.class]
  * 특정 예외 발생 시 rollback한다.
* rollbackForClassName=["Name"]
  * 특정 예외 발생 시 rollback한다.
* timeout
  * 지정한 시간(초) 내에 메소드 수행이 완료되지 않으면 rollback 한다. 디폴트 설정으로 트랜잭션 시스템의 제한시간을 따른다. (-1일 경우 timeout을 사용하지 않는다)
* readOnly
  * 트랜잭션을 읽기 전용으로 설정한다. 특정 트랜잭션 안에서 쓰기 작업이 일어나는 것을 의도적으로 방지하기 위해 사용된다. insert,update,delete 작업이 진행되면 예외가 발생한다.

### isolation 

* Default
  * 사용하는 DB 드라이버의 디폴트 설정을 따른다. 대부분 READ_COMMITED를 기본 격리수준으로 설정한다.
* READ_COMMITED
  * 트랜잭션이 커밋하지 않은 정보는 읽을 수 없다. 하지만 트랜잭션이 읽은 로우를 다른 트랜잭션에서 수정 할 수 있다. 그래서 트랜잭션이 같은 로우를 읽었어도 시간에 따라서 다른 내용이 발견될 수 있다.
* READ_UNCOMMITED
  * 가장 낮은 격리 수준이다.  트랜잭션이 커밋되기 전에 그 변화가 다른 트랜잭션에 그대로 노출된다. 하지만 속도가 빠르기 떄문에 데이터의 일관성이 떨어지더라도, 성능 극대화를 위해 의도적으로 사용하기도 한다.
* REPEATABLE_READ
  * 트랜잭션이 읽은 로우를 다른 트랜잭션에서 수정되는 것을 막아준다. 하지만 새로운 로우를 추가하는 것은 제한하지 않는다.
* SERIALIZABLE
  * 가장 강력한 트랜잭션 격리 수준이다. 여러 트랜잭션이 동시에 같은 테이블 로우에 액세스하지 못하게 한다. 가장 안전하지만 가장 성능이 떨어진다.


### rollbackfor 이해
@Transaction 아무 속성을 주기 않으면 다음과 같이 이해 한다.
```
@Transaction(rollbackFor = {RuntimeException.class, Error.class})
```
스프링은 RuntimeException 과 Error를 기본적으로 롤백 정책으로 이해 한다.

에러에 관한 내용은 [Java Exception 문서](../Exception.md) 참고

체크 예외를 커밋 대상으로 삼는 이유는 체크 예외가 예외 적인 상황에서 사용 되기 보다는 리턴 값을 대신 해서 비즈니스 적인 의미를 담은 결과로 돌려 주는 용도로 사용 되기 때문이다.
스프링에서는 데이터 엑세스 기술의 예외를 런타임 예외로 전환해서 던지므로 런타임 예외만 롤백 대상으로 삼는다.


#### !? 고민 거리 : 왜 Checked Exception은 롤백 정책에 포함되지 않는 것인가?
```text
체크 예외를 롤백에서 제외 한 이유( 커밋 대상으로 삼는 이유 보다는 이표현이 더 적합한것 같다.)는 이 에러는 처리가 강제 되기 때문으로 생각된다. 
반드시 처리가 강제 되었기 때문에 개발자는 소스 상에서 해당 예외에 대해서 인지를 하고 처리를 해야 한다.
(예를 들어 파일이 없는 경우 기본 파일을 생성하게 하고 계속 진행하게 한다거나 할수 있다.- 예외 복수 전략) 
따라서 개발자가 직접 핸들링 해서 롤백이 필요 하면 RuntimeException을 다시 만들어 날리거나 그냥 무시하게 하거나 할수 있다는 이야기다. 
이미 개발자의 의도가 반영되어 처리된 사항에 간섭을 하지 않으려는게 아닐까 싶다.
```

하지만 checked Exception이 발생해도 복구 하거나 보상 처리가 안되는 경우가 많다.
그럴 경우 rollback이 되지 않기 때문에 RuntimException(Unckecked Exception)을 새로 발생 시켜 주는 방식으로 처리를 해야 한다.
(사용자의 입력을 처리하다 문제가 발생하는 경우 - 기존것을 rollback 처리 하고 사용자에게 다시 입력을 받아야 한다.)
JPA에 구현체를 가져다 사용하더라도 Checked Exception을 직접 처리하지 않고 있는 이유도 다 적절한 RuntimeException으로 예외를 던져주고 있기 때문이다.


### Tip

* 트랜젝션 이름 확인하기
```
TransactionSynchronizationManager.getCurrentTransactionName()
```



### 참조
* [선언적 트랜잭션 @Transactional](https://bamdule.tistory.com/51)
* [RollbackFor 이해](https://pjh3749.tistory.com/269)
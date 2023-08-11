# Spring boot 멀티 노드 환경 에서 Batch 작업 중복 실행 방지를 위한 조사

## Shared Lock 사용

DB 를 통해서 작업을 공유하고 이를 통해서 동시 실행을 막는 방식을 사용한다. 

```java

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FooSchedule {

    // 매일 4시 30분 0초에 단 1개의 노드에서만 스케쥴 작업을 실행
    @Scheduled(cron = "0 30 4 * * *")
    @SchedulerLock(name = "aSchedule", lockAtLeastFor = "30s", lockAtMostFor = "1m")
    fun aSchedule() {
        // 스케쥴로 실행될 내용 작성
    }
}

```


이 방식을 진짜 동시 실행을 막는 작업을 할 뿐이다.  
실행에서 획수를 명확하게 보장 해야 하는 경우는 사용이 어려울것 같다.
노드마다 시스템 시간의 미세한 차이가 있을 수 있기 때문에 작업이 아주 빨리 끝나는 경우, 락이 설정되었는데도 작업이 중복 실행되는 경우가 있다. 그때 lockAtLeastFor 값을 설정해서 최소 lock 시간을 보장한다. 


### 참조 
* [Spring Boot, ShedLock, 멀티 노드 환경에서 특정 스케쥴 작업의 중복 실행 방지하기](https://jsonobject.tistory.com/601)  


## Quartz clustering


### 참조
* [Quartz clustering](https://aljjabaegi.tistory.com/679)  



## Spring Batch 


### 참조 
* [Spring batch](https://khj93.tistory.com/entry/Spring-Batch%EB%9E%80-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B3%A0-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0)



## Spring Cloud Data Flow


### 참조 
* [스프링 클라우드 데이터 플로우](https://godekdls.github.io/Spring%20Cloud%20Data%20Flow/batch-developer-guides.getting-started/)  
* [Spring Cloud Data Flow](https://kong-dahye.tistory.com/7)  



## Jenkins 


### 참조 
* [Spring Batch 관리 도구로서의 Jenkins](https://jojoldu.tistory.com/489)  
* 






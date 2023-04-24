# Rest Api Response 


REST(Representational State Transfer)는 분산 하이퍼미디어 시스템을 위한 소프트웨어 아키텍처이다.


## HTTP Status Code (상태 코드)

2XX Success
4.1. 200 OK
4.2. 201 Created
4.3. 202 Accepted
4.4. 204 No Content

4XX Client errors
5.1. 400 Bad Request
5.2. 401 Unauthorized
5.3. 403 Forbidden
5.4. 404 Not Found
5.5. 405 Method Not Allowd
5.6. 409 Conflict
5.7. 429 Too many Requests

5XX Server errors

### 요청과 응답의 단계

1. 시스템 단계
    * DDos
      * 429 : Too Many Requests
2. 인증과 권한 단계
    * 인증 체크 단계 - 인증에 관한 내용을 체크 한다.
      * 401 : Unauthorized - 인증키 의 오류등 접근에 대한 권한 자체게 없는 경우
    * 권한 체크 단계 - 해당 리소스에 대해 접근 권한에 대해서 체크 한다.
      * 403 : Forbidden - 인증키는 정상이나 해당 리소스에 대한 접근 권한이 없는 경우
 
3. 비즈니스 로직 처리 단계
   * 파라메터가 검증
     * 400 : Bad Request - 필수 여부
       유효 여부 범위 패턴
     문법 오류  
     잘못된 형식의 요청 
     전달 인자가 잘못되어 구문을 인식하지 못하는 경우 
     서에서 지정한 구문을 충족시키지 않은 경우 




GET, PUT, DELETE 성공시 - 200

POST 성공시 201

유효성검증 실패 - 400

인증/인가 실패 - 401, 403
* 401 : 클라이언트가 인증되지 않았 거나 유효한 인증 정보가 부족 하여 인증이 거부된 상태 -> 로그인 하세요.
* 403 : 인증은 되었으나 권한이 없어서 거부된 상태 -> 등급이 낮아 접근 권한이 없습니다.

리소스 없을시 - 404

리소스 충돌시 - 409

그 외 오류 - 500


403 Forbidden : 클라이언트가 인증은 됐으나 권한이 없기 때문에 작업을 진행할 수 없는 경우
401 Unauthorized : 클라이언트가 인증이 되지 않아 작업을 진행할 수 없는 경우


### 참조 링크

[참조1](https://sanghaklee.tistory.com/61)  
[응답은 어떻게 줘야 할까?](https://jeong-pro.tistory.com/200)  
[응답 보내기](https://devlog-wjdrbs96.tistory.com/197)  
[응답 코드](https://jaeseongdev.github.io/development/2021/04/22/REST_API%EC%97%90%EC%84%9C%EC%9D%98_HTTP_%EC%83%81%ED%83%9C%EC%BD%94%EB%93%9C_%EC%83%81%ED%83%9C%EB%A9%94%EC%8B%9C%EC%A7%80.md/)  
[azure 에러 상태 코드](https://learn.microsoft.com/ko-kr/rest/api/searchservice/http-status-codes)
[HTTP 상태 401(Unauthorized) vs 403(Forbidden) 차이](https://mangkyu.tistory.com/146)  



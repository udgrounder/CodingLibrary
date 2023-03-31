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
    * DDos 같은 시스템 체크 단계
      * 429 : Too Many Requests
2. 인증과 권한 단계
    * 인증 체크 단계 - 인증에 관한 내용을 체크 한다.
      * 401 : Unauthorized - 인증키 의 오류등 접근에 대한 권한 자체게 없는 경우
    * 권한 체크 단계 - 해당 리소스에 대해 접근 권한에 대해서 체크 한다.
      * 403 : Forbidden - 인증키는 정상이나 해당 리소스에 대한 접근 권한이 없는 경우
 
3. 리소스 단계


### 참조 링크

[참조1](https://sanghaklee.tistory.com/61)  
[응답은 어떻게 줘야 할까?](https://jeong-pro.tistory.com/200)  
[응답 보내기](https://devlog-wjdrbs96.tistory.com/197)  


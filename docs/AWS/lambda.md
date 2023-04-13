# Lambda

### Timezone 설정
시간 사용시 Timezone이 UTC 로 되어 있어서 한국 시간을 기준 으로 처리 하려면 환경 변수를 추가해 줘야 한다.
아래 방법중 둘중 하나만 사용 하면 된다. 

* 환경 변수 추가 
  * TZ : Asia/Seoul
* 코드
  * process.env.TZ = 'Asia/Seoul';



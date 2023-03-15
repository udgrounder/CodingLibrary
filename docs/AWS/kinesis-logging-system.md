# AWS Kinesis 를 이용한 로깅 시스템

1. 어플리케이션
2. Logback Kinesis layout
3. Kinesis DataStream
   * 분기 to CloudWatch -> end
4. Kinesis Firehose
5. S3
6. Excute Lambda at S3 filecreate event
7. make Json data
8. CTAS 방식으로 매 시간마다 테이블 갱싱
9. select to insert 방식으로 매시간마다 parquet 테이블에 insert 하기



```plantuml
@startuml
    node "cpt service" as cpt
    actor "Cpt Admin" as admin
    
    cloud AWS {
        cloud "Cloud Watch" as acw
         
        queue "Kinesis Data Stream" as kds
        queue "Kinesis Firehose" as kfh
        
        control "Lamdba for log" as lam_log
        control "Lamdba for parque" as lam_pq
        
        control "CTAS" as ctas
        storage S3 {
            file "S3 for log" as s3_log
            file "Json DB" as db_json
            file "Parquet DB" as db_pq
        }
    }
    
    
    cpt --> acw : metric log
    cpt --> kds : log
    kds --> acw : log
    acw --> admin : 시스템 장애 알림
    kds --> kfh : log
    kfh --> s3_log : log
    s3_log --> lam_log : log file create event
    lam_log --> s3_log : log 파일 재처리 
    s3_log -> ctas : 1시간 마다 실행
    ctas --> db_json : 데이터 적재
    db_json -> lam_pq : 1시간 마다 실행  
    lam_pq --> db_pq : 데이터를 parque 로 적재
    
@enduml
```


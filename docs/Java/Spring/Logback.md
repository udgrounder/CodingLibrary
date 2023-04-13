# Logback 설정

로그 설정을 프로파일에 따라서 사용하기 위해서 아래 처럼 정리를 하였다. 
kinesis 를 통해서 바로 aws로 전송하는 방법도 추가 하여 향후 VM 환경도 고려 하였다. 
환경에 맞게 appender 를 넣어 주면 appender에 맞게 로그가 생성 된다. 물론 여러 개를 사용 해도 된다.

json 과 kinesis를 사용 하는 경우 아래와 같이 build.gradle에 lib를 추가 해줘야 한다.
* 참고로 logstash 를 사용하면 mdc 내부 객체를 밖으로 꺼내 준다. (꽤 편리 하다.)

```groovy
		// logback to json
		implementation group: 'ch.qos.logback.contrib', name: 'logback-jackson', version: '0.1.5'
		implementation group: 'ch.qos.logback.contrib', name: 'logback-json-classic', version: '0.1.5'

		// log for json use logstash
		implementation group: 'net.logstash.logback', name: 'logstash-logback-encoder', version: '7.2'

		// logback to kinesis
		implementation group: 'com.gu', name: 'kinesis-logback-appender', version: '2.1.1'
```


```xml

<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <logger name="jdbc" level="OFF"/>
    <logger name="jdbc.audit" level="OFF"/>
    <logger name="jdbc.resultset" level="OFF"/>
    <logger name="jdbc.connection" level="OFF"/>
    <logger name="jdbc.resultsettable" level="OFF"/>
    <logger name="jdbc.sqltiming" level="OFF"/>
    <logger name="jdbc.sqlonly" level="DEBUG"/>


    <!-- [default] out : stdout, format : normal -->
    <appender name="APPENDER_NORMAL_STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>[%d{HH:mm:ss:SSS}][%X{traceId}][%-5level][%C.%M:%L] %msg%n</Pattern>
        </layout>
    </appender>

    <!-- out : stdout, format : json -->
    <appender name="APPENDER_JSON_STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="net.logstash.logback.layout.LogstashLayout">
            <suffix class="ch.qos.logback.classic.PatternLayout">
                <pattern>%n</pattern>
            </suffix>
            <timestampPattern>yyyy-MM-dd' 'HH:mm:ss.SSS</timestampPattern>
        </layout>
    </appender>

    <!-- out : file, format : normal -->
    <appender name="APPENDER_NORMAL_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_FILE}.log</file>
        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>[%d{HH:mm:ss:SSS}][%X{traceId}][%-5level][%C.%M:%L] %msg%n</Pattern>
        </layout>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_FILE}.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <!-- or whenever the file size reaches 100MB -->
                <!-- <maxFileSize>50MB</maxFileSize> -->
                <maxFileSize>${LOG_FILE_MAX_SIZE:-10MB}</maxFileSize>
                <!-- kb, mb, gb -->
            </timeBasedFileNamingAndTriggeringPolicy>
            <!-- <maxHistory>30</maxHistory> -->
            <maxHistory>${LOG_FILE_MAX_HISTORY:-10}</maxHistory>
        </rollingPolicy>
    </appender>

    <!-- out : file, format : json -->
    <appender name="APPENDER_JSON_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <layout class="net.logstash.logback.layout.LogstashLayout">
            <suffix class="ch.qos.logback.classic.PatternLayout">
                <pattern>%n</pattern>
            </suffix>
            <timestampPattern>yyyy-MM-dd' 'HH:mm:ss.SSS</timestampPattern>
        </layout>
        <file>${LOG_FILE}.json.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_FILE}.%d{yyyy-MM-dd}.%i.json.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <!-- or whenever the file size reaches 100MB -->
                <!-- <maxFileSize>50MB</maxFileSize> -->
                <maxFileSize>${LOG_FILE_MAX_SIZE:-10MB}</maxFileSize>
                <!-- kb, mb, gb -->
            </timeBasedFileNamingAndTriggeringPolicy>
            <!-- <maxHistory>30</maxHistory> -->
            <maxHistory>${LOG_FILE_MAX_HISTORY:-10}</maxHistory>
        </rollingPolicy>
    </appender>

    <!-- [DEV] out : kinesis, format : json -->
    <appender name="DEV_APPENDER_JSON_KINESIS" class="com.gu.logback.appender.kinesis.KinesisAppender">
        <bufferSize>1000</bufferSize>
        <threadCount>20</threadCount>
        <region>ap-northeast-X</region>
        <maxRetries>3</maxRetries>
        <shutdownTimeout>30</shutdownTimeout>
        <streamName>stream_name_log</streamName>
        <encoding>UTF-8</encoding>
        <layout class="net.logstash.logback.layout.LogstashLayout">
            <suffix class="ch.qos.logback.classic.PatternLayout">
                <pattern>%n</pattern>
            </suffix>
            <timestampPattern>yyyy-MM-dd' 'HH:mm:ss.SSS</timestampPattern>
        </layout>
    </appender>


    <!-- [PROD] out : kinesis, format : json -->
    <appender name="PROD_APPENDER_JSON_KINESIS" class="com.gu.logback.appender.kinesis.KinesisAppender">
        <bufferSize>1000</bufferSize>
        <threadCount>20</threadCount>
        <region>ap-northeast-X</region>
        <maxRetries>3</maxRetries>
        <shutdownTimeout>30</shutdownTimeout>
        <streamName>stream_name_log</streamName>
        <encoding>UTF-8</encoding>
        <layout class="net.logstash.logback.layout.LogstashLayout">
            <suffix class="ch.qos.logback.classic.PatternLayout">
                <pattern>%n</pattern>
            </suffix>
            <timestampPattern>yyyy-MM-dd' 'HH:mm:ss.SSS</timestampPattern>
        </layout>
    </appender>

    <!-- 로컬 -->
    <springProfile name="dev">
        <root level="INFO">
            <appender-ref ref="APPENDER_NORMAL_STDOUT" />
<!--            <appender-ref ref="DEV_APPENDER_JSON_KINESIS_CAMPINFO" />-->
        </root>
    </springProfile>

    <!-- 개발 서버 -->
    <springProfile name="test">
        <root level="INFO">
            <appender-ref ref="APPENDER_NORMAL_FILE" />
            <appender-ref ref="DEV_APPENDER_JSON_KINESIS" />
        </root>
    </springProfile>

    <!-- 알파 서버 -->
    <springProfile name="alpha">
        <root level="INFO">
            <appender-ref ref="PROD_APPENDER_JSON_KINESIS" />
        </root>
    </springProfile>

    <!-- 상용 서버 -->
    <springProfile name="real">
        <root level="INFO">
            <appender-ref ref="PROD_APPENDER_JSON_KINESIS" />
        </root>
    </springProfile>



</configuration>

```

### 점검 사항
* 로그 레벨을 수정했는데 로그의 레벨이 적용이 안되는 경우
  * application.yml 파일 에서 선언된 로그 레벨이 우선 이다. 확인해 봐라.

```yaml
logging:
  level:
    com.package: DEBUG
```



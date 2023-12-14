## 통계 정보

json 으로 압축해서 저장된 파일을 mysql DB 에 업로드 하는 프래그램 이다.

- 가상환경 생성 : venv는 가상환경을 저장할 폴더 이름입니다.
    ```
        python3 -m venv venv
    ```

- 가상환경 활성화 : 가상환경을 활성화하면 해당 환경에서만 작업할 수 있습니다. 활성화되면 터미널 프롬프트 앞에 (venv)라는 표시가 나타납니다.
    ```
        source venv/bin/activate
    ```

- 필요한 라이브러리 설치: MySQL Connector 설치
    ```
        pip3 install mysql-connector-python
    ```

- 가상환경 비활성화: 작업이 끝났을 때 가상환경을 비활성화합니다.
    ```
        deactivate
    ```

- Database Table 생성
    ```
    create table cptstat.page_access
    (
        cuid         varchar(64)  null,
        startyn      varchar(1)   null,
        referrer     text null,
        location     text null,
        devicetype   varchar(64)  null,
        appType      varchar(64)  null,
        timestamp    timestamp    null,
        created      datetime     null,
        referrertype varchar(64)  null,
        referrerhost varchar(64)  null,
        adtype       varchar(64)  null
    );

    ```

- 실행하기 : 실행하는 폴더를 기준으로 source_folder 를 설정하고 데이터를 넣으면 됩니다.
    ```
        python3 statJsonToMysql.py
    ```


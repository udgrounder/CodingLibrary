# PostgresQL 설치 및 기본 명령어

## 설치

### Docker 를 이용한 설치

1. Docker에서 PostgresQL image 가져오기 
```
docker pull postgres
```

2. image 잘 가져왔는지 확인 하기 
```
    docker images
```

3. Postgres 컨테이너를 생성하고 진입하기
```
    docker run -d -p 5432:5432 -e POSTGRES_PASSWORD="mysecretpassword" --name PostgreSQL01 postgres
```
(1) docker run : docker image에서 container를 생성한다.  
(2) –name PostgreSQL01 : container의 이름은 PostgreSQL01로 한다.  
(3) -p 5432:5432 : 해당 container의 port forwarding에 대해 inbound/outbound port 모두 5432으로 설정한다.  
(4) -e : container 내 변수를 설정한다.  
(5) POSTGRES_PASSWORD=”암호” : ROOT 암호를 설정 따옴표 내의 내용은 암호이다.  
(6) -d postgres : postgres이라는 이미지에서 분리하여 container를 생성한다.  


4. Docker 의 상태를 확인한다. 
```
    docker ps -a
```

5. 컨테이너 진입 
```
    sudo docker exec --user="root" -it PostgreSQL01 "bash"
    
    or 
    
    docker exec -it PostgreSQL01 bash
```

-> 패스워드는 내 mac 패스워드이다.
둘다 root 로 진입한다. 첫 번째로는 왜 하는지 잘 모르겠네..


## PostgresQL 시작하기

### DB 연결 및 해제

1. postgresql 위치 확인
```
    which psql
```

2. DB 연결
```
    sudo -U psql postgres 
    [or] 
    psql -U postgres
```

3. DB 연결 정보 보기
```
    \conninfo
```


3. DB 연결 해제
```
    \q
    \quit
```


### DB 다루기


1. 데이터베이스 생성
```
    sudo -u postgres createdb [mydbname]    -- shell 에서 생성하는방법 <- docker 에서는 안된다.. 왜 안될까?
    
    CREATE DATABASE [mydbname]; -- 데이터베이스 생성하기
    SELECT nspname FROM pg_catalog.pg_namespace; -- 성성된 데이터 베이스의 스키마 확인 하기
```


2. 데이터베이스 확인
```
    \l
    \list
    SELECT datname FROM pg_database; -- 전체 데이터베이스 이름 출력
    SELECT datname FROM pg_database WHERE datistemplate = false; -- 사용자가 생성한 데이터베이스 이름만 출력
    
```

9. 데이터베이스 선택
```
    \c postgresql
```


9. 현재 선택된 데이터베이스 조회
```
    select current_database();
```



9. 현재 선택된 데이터베이스 내의 모든 테이블 조회
```
    SELECT * FROM PG_TABLES; -- PostgreSQL 내 모든 테이블 이름 조회
    SELECT * FROM PG_TABLES WHERE schemaname='public'; -- 사용자가 생성한 테이블 이름 조회
    SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' ORDER BY table_name; -- 사용자가 생성한 테이블의 이름 정보만 조회

```

9. 테이블 생성 및 간단한 데이터 입력
```
    CREATE TABLE TestTable (ID INT, TestString VARCHAR(20));
    INSERT INTO TestTable VALUES(1, 'number one');
    INSERT INTO TestTable VALUES(2, 'number two');
    SELECT * FROM TestTable;

```

3. 데이터 베이스 삭제
```
    drop database [mydbname]
```


### 사용자 다루기


3. 사용자 생성
```
    create user 사용자명 password 'mypassword';
```

3. 사용자 롤(role) 또는 비밀번호 변경
```
    alter user 사용자명 with password 'mypassword';
    alter user 사용자명 with superuser;
    alter user 사용자명 with createrole;

```

3. 사용자 권한 주기
```
    grant all privileges on database 데이터베이스명 to 사용자명;
```

3. 디비 소유자(Owner) 바꾸기
```
    alter database 데이터베이스명 owner to 사용자명;
```


8. 사용자 조회
```
    select current_user;    -- 현재 사용자 조회 
    \du                     -- 모든 사용자 조회
    \du+                    -- 모든 사용자 조회
```


9. 현재 선택된 데이터베이스 조회
```
    select current_database();
```


## 기타 설정

### 한글 인코딩 설정

기본 인코딩 설정 위치 sudo vi /etc/postgresql/9.5/main/postgresql.conf
```
    client_encoding = 'ko_KR.UTF-8'
    lc_messages = 'ko_KR.UTF-8'
    lc_monetary = 'ko_KR.UTF-8'
    lc_numeric = 'ko_KR.UTF-8'
    lc_time = 'ko_KR.UTF-8'
```

Collate, Ctype 변경
* 필요한 경우에만 변경하세요. 경우에 따라서 sudo -u postgres psql 을 통해 로그인 시 아래와 같은 메시지의 에러가 발생할 수 있습니다. psql: FATAL: database locale is incompatible with operating system DETAIL: The database was initialized with LC_COLLATE “ko_KR.UTF-8”, which is not recognized by setlocale(). HINT: Recreate the database with another locale or install the missing locale.

```
    update pg_database
    set datcollate='ko_KR.UTF-8', datctype='ko_KR.UTF-8'
    where datname=바꾸려는 데이터베이스 이름;
 
```

## 주의 사항

* PostgreSQL에서 문자열 입력 시 single quote를 사용해야 한다.

## 문제

* docker 에서 sudo 실행이 안된다. 그래서 처리 안되는 사항이 있다. 확인 필요











docker.io/library/postgres:latest





docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres








참고 자료
[Docker Hp] : <https://hub.docker.com/_/postgres>  
[Docker install] : <https://xeppetto.github.io/%EC%86%8C%ED%94%84%ED%8A%B8%EC%9B%A8%EC%96%B4/WSL-and-Docker/15-Docker-PostGreSQL/>  
[Docker install] : <https://judo0179.tistory.com/96>  
[postgresQL 초기 명령어] : <https://devlog.jwgo.kr/2018/05/25/getting-start-postgres> 
[postgresql 을 docker로 설치 하기] : <https://emflant.tistory.com/305>
[한글 인코딩] : <https://calox.tistory.com/29>







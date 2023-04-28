# 기본 조작법


### DB 목록 보는법

```shell
\l
# or
\list
```

```sql
SELECT datname FROM pg_database; -- 전체 데이터베이스 이름 출력
    SELECT datname FROM pg_database WHERE datistemplate = false; -- 사용자가 생성한 데이터베이스 이름만 출력
```


### DB 변경 하는법

```shell
\c <database_name>
```

```sql
SET search_path TO <database_name>;
```


### 테이블 목록 조회 
```sql
SELECT *  FROM information_schema.tables WHERE table_schema = 'public';
```


### 사용자 목록 확인 방법
```sql
SELECT usename FROM pg_user;
```


### 사용자 삭제
```sql
DROP USER [username];
```

### DB 삭제 
```sql

```

* 에러 발생시 
  *  [Postgresql] ERROR: cannot drop the currently open database

연결을 헤제 한다.
```sql
SELECT pg_terminate_backend(procpid) FROM pg_stat_activity WHERE datname = '[지우고싶은 db명]';
```
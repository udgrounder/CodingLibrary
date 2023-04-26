# Port 확인해서 프로세스 죽이는 방법


포트 번호로 프로세스 확인
```shell
lsof -i :[portNum]
```

프로세스 죽이기
```shell
kill -9 [PID]
```

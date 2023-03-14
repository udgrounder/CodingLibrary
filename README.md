# MY 코딩 라이브러리 

코딩을 하면서 참고 할 만한 소스를 모아 정리해 놓은 저장소 이다.

기존에는 따로 모아 놓지 않았는데 점점 양이 많아 지고 시간의 소요 및 계속 반복해서 찾게 된느 일이 발생함에 따라서 정리를 해놓을 필요성이 발생해 정리 하게 되었다.

각 서브 그룹의 서브 프로젝트 들이 참고할 라이브러리로 이해 하기 쉽게 모아 놓을 생각이다.

현재 root 프로젝트에 있는 gradle build 파일도 재사용 가능 코드 라이브러리 중 하나이다.

## gradle 설명
빌드를 편하게 하기 위해서 다음 빌드 파일을 만들었다.
* build.gradle
* settings.gradle
* packageInfo.json

pagkageInfo.json 파일을 설정하고 build 하면 설정된데로 서브 프로젝트를 생성하고 필요한 기본 파일들을 설정해 준다. 

서브 프로젝트 리스트에 새로 생성되는 프로젝트를 추가 하면 된다. 

```
{
"name": "ProjName", // 서브 프로젝트 명
"group": "spring",  // 서브 프로젝트 그룹
"rootPkg": "todo",  // 서브 프로젝트의 root 패키지
"type": "bootJar"   // 서브 프로젝트의 타입
}
```

지원하는 서브 프로젝트의 타입은 다음과 같다. 
* jar : java jar module
* bootWar : spring bootable war
* bootJar : spring bootable jar 
* node : node project (not support)







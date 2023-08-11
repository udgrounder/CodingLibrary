# Optional

API공식문서에서는 Optional을 리턴값으로만 쓰기를 권장합니다.

[ 올바른 Optional 사용법 가이드 ]
* Optional 변수에 Null을 할당하지 말아라  
* 값이 없을 때 Optional.orElseX()로 기본 값을 반환하라  
* 단순히 값을 얻으려는 목적으로만 Optional을 사용하지 마라  
* 생성자, 수정자, 메소드 파라미터 등으로 Optional을 넘기지 마라  
* Collection의 경우 Optional이 아닌 빈 Collection을 사용하라  
* 반환 타입으로만 사용하라  


### 참조 링크
* [Optional](https://catsbi.oopy.io/81ee5bdc-6825-46f8-b1d2-c608ab2d6465)
* [Optional 사용가이드](https://mangkyu.tistory.com/203)  
* 
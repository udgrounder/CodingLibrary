# Scheduled Annotation

스프링에서 간편하게 스케쥴링으로 사용 가능한 방법이 scheduled annotation 이다.

그런데 사용시에 몇가지 주의 사항이 필요하다. 

## scheduled pool 의 사용
* 기본적으로 pool 의 사이즈는 1로 되어 있다. 
  * 따라서 1개의 thread를 이용해서 처리 되기 때문에 여러 스케쥴의 경우 스케쥴이 밀리게 된다 따라서 pool 사이즈를 조절하고 pool 사용시 @async 설정도 해줘야 한다.
  * 
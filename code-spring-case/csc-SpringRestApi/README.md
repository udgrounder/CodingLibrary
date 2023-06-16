# csc-SpringRestApi



## patch 사용시 db 업데이트 에 관한 고민

DB update 시에 null 처리는 어떻게 해야 할까?  
이런 경우 null을 체크 해서 업데이트 방식이 mybatis 와는 적합 하지 않는 것으로 보인다.   
JPA 에서 처리는 좀더 쉬울것 같다는 생각이 든다.

### 업데이트 단계에 따른 스텝 정리 
수정 된것 만 반영할 쉬운 방법이 있는지 고민 필요


* step 1. 일단 수정 또는 변경할 단위를 파악한다.  
* step 2. 요청이 리소스 깂을 Optionals(null)로 설정되어  삭제를 요청 하는지 확인 한다.    
    - step 2-1. 리소스 삭제를 진행한다.  
* step 3. 해당 수정이 일부 값 변경을 요청 하는 사항 인지 확인 한다.  
    - step 3-1. 기존 정보를 불러 온다.  
    - step 3-2. 기존 정보에 변경된 부분만 설정 하고 업데이트 한다.  

"step 3"을 쉽게 할 방법이 없는지 고민이 된다.


아래는 Optional 변수가 포함된 객체를 업데이트하는 예제 입니다.

먼저, 객체 클래스에는 Optional 변수와 함께 업데이트 할 다른 필드들이 포함 되어 있어야 합니다:
```java
    public class MyObject {
    private int id;
    private Optional<String> value;
    private String otherField1;
    private int otherField2;
    
        // 생성자, getter, setter 등 필요한 코드 작성
    }

```

MyObject 클래스에는 value 외에도 업데이트할 다른 필드들인 otherField1, otherField2 등이 포함되어 있습니다.

매퍼 인터페이스에서는 if 태그를 사용하여 null 체크를 수행하고 업데이트할 필드를 선택할 수 있습니다:

```java
    public interface MyMapper {
        void updateObject(MyObject myObject);
    }
```
매퍼 XML 파일에서는 if 태그를 사용하여 null 체크를 수행하고 업데이트할 필드를 선택할 수 있습니다:

```xml
<!-- MyMapper.xml -->
<mapper namespace="com.example.MyMapper">
    <update id="updateObject">
        UPDATE my_table
        SET
        <if test="value != null and value.isPresent()">value = #{value.get()},</if>
        <if test="otherField1 != null">other_field1 = #{otherField1},</if>
        <if test="otherField2 != 0">other_field2 = #{otherField2},</if>
        WHERE id = #{id}
    </update>
</mapper>
```

매퍼 XML 파일에서는 if 태그를 사용하여 각 필드의 null 체크를 수행하고 업데이트할 필드를 선택적으로 포함합니다. 필드가 null이 아닌 경우에만 해당 필드를 업데이트합니다.

Java 코드에서는 MyBatis를 사용하여 객체를 업데이트합니다:

```java
public class Main {
public static void main(String[] args) {
SqlSessionFactory sqlSessionFactory = ... // SqlSessionFactory를 생성하는 코드

        try (SqlSession session = sqlSessionFactory.openSession()) {
            MyMapper mapper = session.getMapper(MyMapper.class);
            
            MyObject myObject = new MyObject();
            myObject.setId(1);
            myObject.setValue(Optional.of("New Value"));
            myObject.setOtherField1("Other Value");
            
            mapper.updateObject(myObject);
            session.commit();
        }
    }
}
```
Java 코드에서는 MyObject 객체를 생성하고 필요한 필드를 설정한 후, 매퍼 인터페이스의 updateObject 메서드를 호출하여 객체를 업데이트합니다. 변경 사항을 커밋합니다.

이 예제를 참고하여 MyBatis의 if 태그를 사용하여 Optional 변수가 포함된 객체를 업데이트할 수 있습니다.
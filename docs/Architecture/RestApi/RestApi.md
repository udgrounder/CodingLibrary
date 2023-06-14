# Rest API 정보

## Restful api 란


### 참조링크
[Rest api](https://thalals.tistory.com/335)


## method 별 사용 케이스 

POST
* 생성
* 프로세스 처리
* on / off 같은 명령
* 단 이미 존재 하면 에러

GET
* 조회

PUT
* 기존의 것을 대체 한다.
* 만약 해당하는 리소스가 없으면 생성 한다.
* 부분 값을 처리 하지 않고 모든 값을 대체 한다. => 값이 없는 경우 값을 지운다.
 

PATCH
* update
* partial update
* 기존의 것을 값을 바꾼다. 명확하게 들어온 값에 대해서만 변경한다.
* 리소스가 없으면 에러  

DELETE
* delete
* 리소스가 없으면 에러   

### 참조 링크  
[POST 와 PUT](https://kingjakeu.github.io/study/2020/07/15/http-post-put/)  
[HTTP Method](https://velog.io/@jewon119/TIL00.-HTTP-HTTP-Method)   
  
  
  
  
## patch 를 이용한 데이터 업데이트 방식

jsonpatch 와 jsonmergepatch 

** 테스트 소스 생성 및 구현을 통해서 검증이 필요 하다.


### Chat gpt 와의 대화를 통한 Optional 사용에 대한 설명과 예제  

결론적으로 이야기 하자면 모두 Optional로 모두 만들어서 처리 해야 실제 어떤 값을 반영 할지 판단이 가능 하다.

아무것도 설정하지 않은 경우
optional.isPresent() => false
optional.isEmpty() => true

null을 설정한 경우
optional.isPresent() => true
optional.isEmpty() => false
optional.get() => null


보내는 쪽에서는 아무값도 설정하지 않으면 json string 을 만들때는 필드 자체가 생성되지 않는다. 
하지만 이를 역직렬화시에는 null 을 설정한것과 설정하지 않은 것의 차이를 구분할 수 없어 Optional 객체를 사용해서 처리 해야 한다.



---------

JSON 문자열에 해당 필드가 없는 경우:

Optional 타입의 멤버 변수에 대응되는 JSON이 없는 경우, Optional 객체는 비어있는 상태로 할당됩니다.
이때 Optional.isPresent() 메서드는 false를 반환하고, Optional.isEmpty() 메서드는 true를 반환합니다.
예시:

java
Copy code
// JSON 문자열: {}
class User {
private Optional<String> name;
// Getter, Setter, 등 필요한 코드들...
}

User user = objectMapper.readValue(jsonString, User.class);
System.out.println(user.getName().isPresent());  // false
System.out.println(user.getName().isEmpty());    // true
JSON 문자열에 해당 필드가 있고 값이 null인 경우:

JSON 문자열에 해당 필드가 있고 값이 null인 경우, Optional 객체는 내부 값이 null인 상태로 할당됩니다.
이때 Optional.isPresent() 메서드는 true를 반환하고, Optional.isEmpty() 메서드는 false를 반환합니다.
예시:

java
Copy code
// JSON 문자열: {"name": null}
class User {
private Optional<String> name;
// Getter, Setter, 등 필요한 코드들...
}

User user = objectMapper.readValue(jsonString, User.class);
System.out.println(user.getName().isPresent());  // true
System.out.println(user.getName().isEmpty());    // false
System.out.println(user.getName().get());        // null
위의 예제에서는 첫 번째 예제에서 JSON 문자열에 해당 필드가 없는 경우 user.getName().isPresent()는 false를 반환하고, user.getName().isEmpty()는 true를 반환합니다. 두 번째 예제에서는 JSON 문자열에 해당 필드가 있고 값이 null인 경우 user.getName().isPresent()는 true를 반환하고, user.getName().isEmpty()는 false를 반환합니다.








spring boot 환경에서 rest api 의 jsonmerge patch 의 예제를 보여줘
ChatGPT
물론입니다! Spring Boot 환경에서 JSON Merge Patch를 사용하는 예제를 보여드리겠습니다.

먼저, 의존성을 추가해야 합니다. pom.xml 파일에 다음 의존성을 추가합니다.

xml
Copy code
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
<groupId>com.fasterxml.jackson.core</groupId>
<artifactId>jackson-databind</artifactId>
</dependency>
다음으로, REST 컨트롤러를 만들어 보겠습니다. UserController.java 파일을 생성하고 다음 코드를 작성합니다.

java
Copy code
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ObjectMapper objectMapper;

    public UserController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody JsonNode patch) {
        User existingUser = getUserById(id);

        try {
            // 기존 사용자 정보에 패치를 적용합니다.
            User patchedUser = objectMapper.readerForUpdating(existingUser).readValue(patch);
            // 패치된 사용자 정보를 저장하거나 필요한 로직을 수행합니다.
            saveUser(patchedUser);

            return patchedUser;
        } catch (Exception e) {
            // 처리 중 에러가 발생한 경우 예외 처리를 수행합니다.
            throw new RuntimeException("Failed to apply patch to user.", e);
        }
    }

    private User getUserById(Long id) {
        // 실제로는 데이터베이스나 다른 소스에서 사용자 정보를 가져올 수 있습니다.
        // 예시를 위해 간단히 하나의 사용자를 생성하여 반환합니다.
        User user = new User();
        user.setId(id);
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        return user;
    }

    private void saveUser(User user) {
        // 실제로는 데이터베이스나 다른 저장소에 사용자 정보를 저장하는 로직을 구현합니다.
        // 여기서는 단순히 출력으로 대체합니다.
        System.out.println("Saving user: " + user);
    }
}
위의 코드에서 @PatchMapping("/{id}") 어노테이션은 /users/{id} 엔드포인트에 PATCH 요청이 올 경우 updateUser() 메서드가 실행되도록 설정합니다. @RequestBody 어노테이션은 요청의 본문에 있는 JSON 패치를 JsonNode로 받아옵니다.

updateUser() 메서드에서는 getUserById()를 사용하여 기존 사용자 정보를 가져온 후, objectMapper.readerForUpdating(existingUser).readValue(patch)를 사용하여 기존 사용자 정보에 JSON 패치를 적용합니다. 그리고 패치된 사용자 정보를 저장하거나 필요한 로직을 수행한 후, 패치된 사용자를 반환합니다.

이 예제에서는 간단한





User
이런경우 업데이트를 하지 않기 위해 null 인 항목과 실제 null 로 업데이트를 하기위해 null 인 항목을 구분하려면 어떻게 해야해?
ChatGPT
JSON Merge Patch에서 null 값을 사용하여 항목을 삭제하려는 경우와, 실제로 null 값을 사용하여 해당 항목을 null로 업데이트하려는 경우를 구분해야 할 때는 다음과 같은 방법을 사용할 수 있습니다.

Optional 사용하기: Java 8 이상을 사용하는 경우, Optional을 활용하여 null 값을 구분할 수 있습니다. 예를 들어, 사용자의 이름을 업데이트하고자 할 때, 이름이 null인 경우는 삭제하려는 경우로 간주하고, 이름이 실제로 null로 업데이트하고자 하는 경우는 구분할 수 있습니다.
java
Copy code
@PatchMapping("/{id}")
public User updateUser(@PathVariable Long id, @RequestBody JsonNode patch) {
User existingUser = getUserById(id);

    try {
        JsonNode nameNode = patch.get("name");
        if (nameNode != null) {
            String newName = nameNode.isNull() ? null : nameNode.asText();
            existingUser.setName(newName);
        }

        // 기타 업데이트 로직...

        saveUser(existingUser);

        return existingUser;
    } catch (Exception e) {
        throw new RuntimeException("Failed to apply patch to user.", e);
    }
}
JSON 패치의 일부를 검사하기: JSON 패치를 검사하여 null 값을 구분할 수도 있습니다. 예를 들어, 패치에 null 값을 포함하는 경우 삭제로 간주하고, 실제 null 값을 사용하여 업데이트하고자 하는 경우를 구분할 수 있습니다.
java
Copy code
@PatchMapping("/{id}")
public User updateUser(@PathVariable Long id, @RequestBody JsonNode patch) {
User existingUser = getUserById(id);

    try {
        if (patch.has("name")) {
            JsonNode nameNode = patch.get("name");
            if (nameNode.isNull()) {
                existingUser.setName(null);
            } else {
                existingUser.setName(nameNode.asText());
            }
        }

        // 기타 업데이트 로직...

        saveUser(existingUser);

        return existingUser;
    } catch (Exception e) {
        throw new RuntimeException("Failed to apply patch to user.", e);
    }
}
위의 예제에서는 patch.has("name")을 사용하여 패치에 "name" 필드가 포함되어 있는지 검사한 후, 해당 값이 null인지 확인하여 삭제 또는 실제 null 값을 사용하여 업데이트하도록 합니다.

이러한 방법을 활용하여 JSON Merge Patch에서 null 값을 사용하여 항목을 삭제하거나 실제로 null로 업데이트하고자 할 때 구분할 수 있습니다.





User
그럼 클라이언트 입장에서 오두 Optional 객체로 선언해야 하는거야?
ChatGPT
네, 클라이언트에서 Optional 객체를 사용하여 JSON Merge Patch 요청을 보낼 수 있습니다. 이렇게 하면 클라이언트가 업데이트할 필드가 실제로 null인지 아니면 삭제를 의도한 것인지 구분할 수 있습니다.

클라이언트는 업데이트할 필드를 Optional 객체로 감싸고, 필드 값을 명시적으로 설정하거나 null로 설정하여 삭제를 의도할 수 있습니다. 그런 다음 해당 필드를 JSON으로 직렬화하여 서버로 전송하면 됩니다.

예를 들어, 클라이언트가 사용자의 이름을 업데이트하고자 할 때, 다음과 같이 Optional 객체를 사용할 수 있습니다.

java
Copy code
Optional<String> newName = Optional.ofNullable("John Doe");  // 업데이트할 신규 이름 설정

Map<String, Optional<String>> patch = new HashMap<>();
patch.put("name", newName);

String requestBody = new ObjectMapper().writeValueAsString(patch);

// requestBody를 서버로 전송하여 JSON Merge Patch 요청을 수행
위의 예제에서는 Optional.ofNullable()을 사용하여 업데이트할 신규 이름을 설정합니다. 만약 이름을 삭제하고자 한다면 Optional.empty()를 사용하면 됩니다.

클라이언트에서는 업데이트할 필드를 Optional로 감싸고 JSON으로 직렬화하여 서버로 전송하여 구분할 수 있습니다. 서버에서는 이러한 Optional 객체를 사용하여 업데이트를 수행하면서 null 값과 삭제를 구분할 수 있습니다.





User
Option 값이 null 이면 null 로 자동으로 가게 하는 샘플 알려줘
ChatGPT
클라이언트에서 Optional 값이 null인 경우를 자동으로 null로 설정하려면 다음과 같이 처리할 수 있습니다.

java
Copy code
Optional<String> newName = Optional.ofNullable(null);  // 업데이트할 신규 이름 설정

Map<String, String> patch = new HashMap<>();
patch.put("name", newName.orElse(null));

String requestBody = new ObjectMapper().writeValueAsString(patch);

// requestBody를 서버로 전송하여 JSON Merge Patch 요청을 수행
위의 예제에서는 Optional.ofNullable()을 사용하여 업데이트할 신규 이름을 설정합니다. 만약 이름이 null인 경우 Optional.ofNullable(null)로 설정하면 됩니다.

patch 맵에는 업데이트할 필드를 담고 있으며, newName.orElse(null)을 사용하여 Optional 값이 null인 경우에는 null로 설정하도록 합니다.

그러면 서버로 전송되는 JSON Merge Patch 요청의 name 필드 값은 클라이언트의 Optional 값이 null인 경우 null로 설정됩니다. 이를 통해 클라이언트에서 자동으로 null 값을 설정할 수 있습니다.





User
Optional 인 경우 json serialize 에 문제가 없어?
ChatGPT
Optional 객체는 기본적으로 JSON 직렬화에 문제가 발생하지 않습니다. 대부분의 JSON 직렬화 라이브러리들은 Optional 객체를 올바르게 처리할 수 있도록 지원하고 있습니다.

Spring Boot에서 기본적으로 사용되는 Jackson 라이브러리도 Optional을 처리할 수 있습니다. Optional 객체를 JSON으로 직렬화하면 해당 필드가 존재하는 경우 값이 직렬화되고, Optional이 비어있는 경우 해당 필드가 생략됩니다.

예를 들어, 다음은 Optional 필드를 가진 User 클래스를 JSON으로 직렬화하는 예제입니다.

java
Copy code
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class User {
private Optional<String> name;

    public User(Optional<String> name) {
        this.name = name;
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        User user = new User(Optional.of("John Doe"));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        System.out.println(json);
    }
}
위의 예제에서는 Optional<String> name 필드를 가진 User 클래스를 정의하고, Optional.of("John Doe")로 이름을 설정합니다. 그리고 ObjectMapper를 사용하여 해당 객체를 JSON 문자열로 직렬화합니다.

실행 결과는 다음과 같습니다.

json
Copy code
{"name":"John Doe"}
옵셔널 값이 존재하는 경우 해당 필드가 정상적으로 직렬화되며, 옵셔널 값이 비어있는 경우 해당 필드는 생략됩니다. JSON 직렬화 라이브러리들이 Optional을 올바르게 처리하므로 대부분의 경우 문제 없이 작동합니다.

다만, 일부 JSON 직렬화 라이브러리는 특정 설정을 추가해야 Optional을 올바르게 처리할 수 있을 수도 있으므로, 사용하는 라이브러리의 문서를 확인하여 추가 설정이 필요한지 확인하는 것이 좋습니다.





User
json 역직렬화의 경우 null 이면 Optional 객체의 에 null 이 들어 가는지  Optional 객체 자체가 null로 되는거야?
ChatGPT
JSON 역직렬화 과정에서 null 값이 JSON에 포함되어 있다면 Optional 객체 자체가 null로 설정되는 것이 아니라, Optional 객체의 내부에 있는 값이 null로 설정됩니다.

예를 들어, 다음은 JSON 문자열을 User 객체로 역직렬화하는 예제입니다.

java
Copy code
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class User {
private Optional<String> name;

    public User() {
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        String json = "{\"name\": null}";

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);

        System.out.println(user.getName());
    }
}
위의 예제에서는 JSON 문자열 {"name": null}을 User 객체로 역직렬화합니다. JSON에는 "name" 필드가 null로 설정되어 있습니다.

실행 결과는 다음과 같습니다.

csharp
Copy code
Optional[null]
JSON 역직렬화 과정에서 "name" 필드가 null로 설정되면, Optional 객체의 내부에 있는 값이 null로 설정됩니다. 따라서 user.getName()을 호출하면 Optional 객체가 반환되며, 이 Optional 객체의 내부 값은 null입니다.

Optional 객체 자체가 null로 설정되는 것은 아니며, Optional 객체는 항상 존재하며 내부 값을 가지고 있습니다. 따라서 Optional 객체의 메서드인 isPresent()를 사용하여 해당 필드가 존재하는지 여부를 확인하고, orElse() 또는 orElseGet() 메서드를 사용하여 내부 값을 가져올 수 있습니다.





User
그럼 json 값에 null 이 있으면 Optional 객체에 null 로 설정되고 항목이 없으면 null 값이 설정되는거야?
ChatGPT
JSON 값에 null이 있을 때와 항목이 없을 때의 동작은 다음과 같습니다.

JSON 값에 null이 있을 때:

JSON 값이 null인 경우, Optional 객체의 내부 값은 null로 설정됩니다.
예를 들어, JSON 문자열 {"name": null}을 역직렬화하는 경우, Optional 필드의 내부 값은 null로 설정됩니다.
항목이 없을 때:

JSON에 해당 필드가 없는 경우, Optional 객체 자체는 존재하며 내부 값은 비어있습니다 (empty).
예를 들어, JSON 문자열 {"age": 25}을 역직렬화하는 경우, Optional 필드는 존재하지만 내부 값은 비어있습니다.
이를 코드로 표현하면 다음과 같습니다.

java
Copy code
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class User {
private Optional<String> name;

    public User() {
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        String jsonWithNull = "{\"name\": null}";
        String jsonWithoutField = "{\"age\": 25}";

        ObjectMapper mapper = new ObjectMapper();

        User userWithNull = mapper.readValue(jsonWithNull, User.class);
        System.out.println(userWithNull.getName());  // Optional[null]

        User userWithoutField = mapper.readValue(jsonWithoutField, User.class);
        System.out.println(userWithoutField.getName());  // Optional.empty
    }
}
위의 예제에서는 JSON 문자열을 User 객체로 역직렬화합니다. jsonWithNull에는 "name" 필드가 null로 설정되어 있고, jsonWithoutField에는 "name" 필드가 없습니다.

실행 결과는 다음과 같습니다.

mathematica
Copy code
Optional[null]
Optional.empty
따라서 JSON 값에 null이 있으면 Optional 객체의 내부 값은 null로 설정되며, 항목이 없으면 Optional 객체는 존재하지만 내부 값은 비어있게 됩니다.





User
class 멤버 변수는 있고 json 스트링에 없는 경우를 물어 보는거야
ChatGPT
클래스의 멤버 변수는 존재하지만 JSON 문자열에 해당 필드가 없는 경우에는 JSON 역직렬화 과정에서 해당 멤버 변수의 값은 기본값으로 설정됩니다.

기본적으로 Jackson 라이브러리는 JSON 문자열에 해당 필드가 없을 경우, 기본값으로 자료형에 따른 기본 값을 설정합니다. 예를 들어, 정수형 변수의 기본값은 0, 문자열 변수의 기본값은 null입니다.

다음은 해당 상황을 보여주는 예제입니다.

java
Copy code
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {
private String name;
private int age;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) throws Exception {
        String json = "{\"name\": \"John Doe\"}";

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);

        System.out.println(user.getName());  // John Doe
        System.out.println(user.getAge());   // 0 (기본값)
    }
}
위의 예제에서는 JSON 문자열 {"name": "John Doe"}을 역직렬화하여 User 객체로 변환합니다. JSON에는 "name" 필드만 포함되어 있으며, "age" 필드는 없습니다.

실행 결과는 다음과 같습니다.

Copy code
John Doe
0
JSON 문자열에는 "age" 필드가 없지만, 클래스의 age 멤버 변수는 기본적으로 0으로 초기화되므로 역직렬화된 객체의 age 필드 값은 0입니다.

따라서 클래스의 멤버 변수는 있지만 JSON 문자열에 해당 필드가 없는 경우, 해당 멤버 변수는 기본값으로 초기화됩니다.





User
대응되는 member 변수의 타입이 일반 객체일때와 Optional 일때 다르게 동작한다는 거야? 이를 테면 Optional 은 Optional 객체에 null 이 할당되어 들어 가고 String 객체일때는 null 이  들어 가는식으로
ChatGPT
네, 일반 객체와 Optional 객체의 동작은 다를 수 있습니다.

일반 객체 (Non-Optional):

JSON 문자열에 해당 필드가 없는 경우, 해당 멤버 변수의 값은 기본값으로 설정됩니다. 일반적으로 자료형의 기본값으로 초기화됩니다. 예를 들어, 정수형 변수의 기본값은 0, 문자열 변수의 기본값은 null입니다.
Optional 객체:

JSON 문자열에 해당 필드가 없는 경우, 해당 Optional 객체는 존재하지만 내부 값은 비어있습니다 (empty). Optional 객체 자체가 null로 설정되는 것이 아니라 내부 값이 null로 설정됩니다.
예를 들어, 다음은 일반 객체와 Optional 객체를 포함하는 클래스의 예제입니다.

java
Copy code
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class User {
private String name;
private Optional<String> address;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getAddress() {
        return address;
    }

    public void setAddress(Optional<String> address) {
        this.address = address;
    }

    public static void main(String[] args) throws Exception {
        String json = "{\"name\": null}";

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);

        System.out.println(user.getName());       // null
        System.out.println(user.getAddress());    // Optional.empty
    }
}
위의 예제에서는 JSON 문자열 {"name": null}을 역직렬화하여 User 객체로 변환합니다. JSON에는 "name" 필드만 포함되어 있으며, "address" 필드는 없습니다.

실행 결과는 다음과 같습니다.

php
Copy code
null
Optional.empty
일반 객체인 name 멤버 변수의 경우, JSON 문자열의 "name" 필드가 null이므로 멤버 변수 값도 null이 됩니다.

Optional 객체인 address 멤버 변수의 경우, JSON 문자열에 "address" 필드가 없으므로 해당 Optional 객체는 존재하지만 내부 값은 비어있습니다 (empty).

따라서, JSON 문자열에 해당 필드가 없는 경우에도 일반 객체와 Optional 객체의 동작은 다를 수 있습니다.


jsonmergepatch 방식에서 Optional 을 사용하지 않고 json 스트링을 만들 방법이 있어?
ChatGPT
네, json-merge-patch 방식에서 Optional을 사용하지 않고 JSON 문자열을 생성하는 방법이 있습니다. 아래의 예제에서는 Jackson 라이브러리를 사용하여 JSON 문자열을 생성하는 방법을 보여드리겠습니다:

java
Copy code
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonMergePatchExample {
public static void main(String[] args) throws Exception {
// 원본 JSON
String originalJson = "{\"name\": \"John\", \"age\": 30}";

        // 변경할 JSON
        String patchJson = "{\"name\": \"Mike\"}";

        // JSON 문자열을 JsonNode로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode originalNode = objectMapper.readTree(originalJson);
        JsonNode patchNode = objectMapper.readTree(patchJson);

        // JsonNode를 병합하여 새로운 JsonNode 생성
        JsonNode mergedNode = mergeJsonNodes(originalNode, patchNode);

        // JsonNode를 JSON 문자열로 변환
        String mergedJson = objectMapper.writeValueAsString(mergedNode);

        // 결과 출력
        System.out.println(mergedJson);
    }

    private static JsonNode mergeJsonNodes(JsonNode originalNode, JsonNode patchNode) {
        if (originalNode instanceof ObjectNode && patchNode instanceof ObjectNode) {
            ObjectNode mergedNode = ((ObjectNode) originalNode).deepCopy();
            mergedNode.setAll((ObjectNode) patchNode);
            return mergedNode;
        }
        return patchNode;
    }
}
위의 예제에서는 mergeJsonNodes() 메서드를 사용하여 두 개의 JsonNode를 병합합니다. 그런 다음, ObjectMapper의 writeValueAsString() 메서드를 사용하여 병합된 JsonNode를 JSON 문자열로 변환합니다.

따라서 Optional을 사용하지 않고도 json-merge-patch 방식으로 JSON 문자열을 생성할 수 있습니다.





User
호출하는 쪽에서 null 인 객체를 설정한것과 아무것도 설정하지 않은것을 구분해서 json String을 만들수 있어?
ChatGPT
네, 호출하는 쪽에서 null인 객체를 설정한 것과 아무 것도 설정하지 않은 것을 구분하여 JSON 문자열을 생성할 수 있습니다. 이를 위해 다음과 같은 방법을 사용할 수 있습니다:

필드에 null을 설정한 경우:

호출하는 쪽에서 해당 필드에 null 값을 설정한 경우, 해당 필드는 JSON 문자열에 포함됩니다.
JSON 문자열에서 해당 필드는 "null" 값으로 표시됩니다.
필드를 설정하지 않은 경우:

호출하는 쪽에서 해당 필드를 설정하지 않은 경우, 해당 필드는 JSON 문자열에 포함되지 않습니다.
다음은 예시 코드입니다:

java
Copy code
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonGenerationExample {
public static void main(String[] args) throws Exception {
User user1 = new User();
user1.setName(null);
user1.setAge(30);

        User user2 = new User();
        user2.setAge(40);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString1 = objectMapper.writeValueAsString(user1);
        System.out.println(jsonString1); // {"name":null,"age":30}

        String jsonString2 = objectMapper.writeValueAsString(user2);
        System.out.println(jsonString2); // {"age":40}
    }
}

class User {
private String name;
private int age;

    // Getters and Setters...

    // 필요한 생성자와 메서드들...
}
위의 예시에서는 User 클래스를 정의하고, name과 age 필드를 갖습니다. 첫 번째 예시에서는 user1 객체에서 name 필드에 null을 설정하고, age 필드에 30을 설정합니다. 이 경우 name 필드는 JSON 문자열에 포함되지만 값은 "null"로 표시됩니다. 두 번째 예시에서는 user2 객체에서 age 필드만 설정하고, name 필드는 설정하지 않습니다. 이 경우 name 필드는 JSON 문자열에 포함되지 않습니다.

따라서 호출하는 쪽에서 null인 객체를 설정한 경우와 설정하지 않은 경우를 구분하여 JSON 문자열을 생성할 수 있습니다.





User
설정하지 않으면 null 값이 자동으로 할당되지 않아?
ChatGPT
정확하게 말씀드리자면, 일반적으로 호출하는 쪽에서 해당 필드를 설정하지 않은 경우, JSON 문자열에 해당 필드가 포함되지 않습니다. 그러나 ObjectMapper의 기본 동작은 해당 필드를 null로 설정하지 않는 한 필드가 JSON 문자열에 표시되지 않는 것입니다. 이것은 Jackson 라이브러리의 기본 동작입니다.

다음은 예시 코드입니다:

java
Copy code
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonGenerationExample {
public static void main(String[] args) throws Exception {
User user = new User();
// user.setName(null);
user.setAge(30);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = objectMapper.writeValueAsString(user);
        System.out.println(jsonString); // {"age":30}
    }
}

class User {
private String name;
private int age;

    // Getters and Setters...

    // 필요한 생성자와 메서드들...
}
위의 예시에서는 User 클래스를 정의하고, name과 age 필드를 갖습니다. user 객체에서 name 필드를 설정하지 않고, age 필드에 30을 설정합니다. ObjectMapper를 사용하여 JSON 문자열을 생성하면 name 필드가 JSON 문자열에 포함되지 않는 것을 확인할 수 있습니다.

따라서 기본적으로 호출하는 쪽에서 해당 필드를 설정하지 않은 경우, 해당 필드는 JSON 문자열에 포함되지 않습니다. null 값을 자동으로 할당하는 것은 아닙니다.





User
그럼 받는 쪽에서는 어떻게 구별해?
ChatGPT
받는 쪽에서는 JSON 문자열을 역직렬화한 후, 필드의 값을 확인하여 설정되지 않은 경우와 null 값인 경우를 구별할 수 있습니다. 이를 통해 호출하는 쪽에서 해당 필드를 설정하지 않은 경우와 null 값을 구별할 수 있습니다.

예를 들어, Jackson 라이브러리를 사용하여 JSON 문자열을 역직렬화할 때, JsonNode나 해당 필드의 타입을 확인하여 값이 설정되지 않았는지 혹은 null인지를 확인할 수 있습니다. 다음은 예시 코드입니다:

java
Copy code
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDeserializationExample {
public static void main(String[] args) throws Exception {
String jsonString1 = "{\"name\":null,\"age\":30}";
String jsonString2 = "{\"age\":40}";

        ObjectMapper objectMapper = new ObjectMapper();

        User user1 = objectMapper.readValue(jsonString1, User.class);
        System.out.println(user1.getName()); // null
        System.out.println(user1.getAge()); // 30

        User user2 = objectMapper.readValue(jsonString2, User.class);
        System.out.println(user2.getName()); // null
        System.out.println(user2.getAge()); // 40
    }
}

class User {
private String name;
private Integer age;

    // Getters and Setters...

    // 필요한 생성자와 메서드들...
}
위의 예시에서는 User 클래스를 정의하고, name과 age 필드를 갖습니다. 첫 번째 예시에서는 JSON 문자열에 "name":null과 "age":30이 포함되어 있습니다. 이를 역직렬화하여 user1 객체로 변환하면 getName() 메서드로 null 값을, getAge() 메서드로 30을 얻을 수 있습니다.

두 번째 예시에서는 JSON 문자열에 "age":40만 포함되어 있습니다. 이를 역직렬화하여 user2 객체로 변환하면 getName() 메서드로 null 값을, getAge() 메서드로 40을 얻을 수 있습니다.

따라서 받는 쪽에서는 역직렬화한 후 필드의 값을 확인하여 설정되지 않은 경우와 null 값인 경우를 구별할 수 있습니다.





User
둘다 name 이 null 이라 null 을 설정한 것과 아예 설정하지 않은것을 구분 못하는데
ChatGPT
안타깝게도 JSON 데이터 자체에서 null 값을 구분할 수는 없습니다. JSON은 값이 null인지 아닌지를 명시적으로 나타내지 않습니다. 따라서 JSON 문자열을 역직렬화하여 객체로 변환할 때 null 값이 설정되었는지 아니면 값 자체가 없는지를 구분할 수는 없습니다.

이러한 경우에는 다른 방법을 사용하여 null 값을 구분해야 할 수 있습니다. 예를 들어, 필드에 null을 설정한 경우 대신 특정 값을 사용하거나 플래그 변수를 추가하여 null 값을 나타낼 수 있습니다. 이를테면 특정 값을 사용하는 경우에는 "null" 대신 빈 문자열이나 특수한 값을 설정할 수 있습니다.

예를 들어, 다음과 같은 방법을 사용하여 null 값을 구분할 수 있습니다:

java
Copy code
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDeserializationExample {
public static void main(String[] args) throws Exception {
String jsonString1 = "{\"name\":\"null\",\"age\":30}";
String jsonString2 = "{\"age\":40}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        User user1 = objectMapper.readValue(jsonString1, User.class);
        System.out.println(user1.getName()); // null
        System.out.println(user1.getAge()); // 30

        User user2 = objectMapper.readValue(jsonString2, User.class);
        System.out.println(user2.getName()); // null
        System.out.println(user2.getAge()); // 40
    }
}

class User {
private String name;
private Integer age;

    // Getters and Setters...

    // 필요한 생성자와 메서드들...
}
위의 예시에서는 Jackson의 JsonInclude.Include.NON_NULL 설정을 사용하여 JSON 문자열에서 null 값을 무시하도록 합니다. 이렇게 설정하면 "name\":\"null\"과 같은 JSON 문자열에서도 name 필드가 null로 설정되지 않고, null 값을 갖는 것처럼 동작합니다.

하지만 이는 일종의 workaround(회피 방법)이며, 원본 JSON 데이터 자체에서 null 값을 구분하는 것은 불가능합니다. JSON 데이터에 null 값을 구분하기 위해서는 추가적인 정보를 사용해야 합니다.








### 참조링크 
[Rest api patch](https://hhjeong.tistory.com/190)  
[jsonmergepath](https://jsonobject.tistory.com/622)   
[Using JSON Patch in Spring REST APIs](https://www.baeldung.com/spring-rest-json-patch)  
[Spring Boot, JsonNullable을 이용하여 HTTP PATCH 구현하기](https://jsonobject.tistory.com/622)  






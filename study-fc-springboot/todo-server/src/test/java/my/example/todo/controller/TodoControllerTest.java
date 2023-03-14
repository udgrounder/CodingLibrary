package my.example.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.example.todo.model.TodoEntity;
import my.example.todo.model.TodoRequest;
import my.example.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TodoControllerTest.class)           // case 1.  특정 controller 만 단위 테스트 하는 경우
@ComponentScan(basePackages = "org.example")    // case 1 에서 404에러가 떨어 져서 컴포넌트를 스캔해준다.
//@TestConfiguration
//@SpringBootTest                               // case 2. 프로젝트의 모든 의존성을 가지고 테스트 한다.
//@AutoConfigureMockMvc                         // case 2 에 같이 포함해야 한다.
//@ActiveProfiles("RESTControllersTest")        // 프로파일을 사용하는경우
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoService todoService;

    private TodoEntity expected;

    @BeforeEach
    void setup() {
        this.expected = new TodoEntity();
        this.expected.setId(123L);
        this.expected.setTitle("TEST TITLE");
        this.expected.setOrder(0L);
        this.expected.setCompleted(false);

    }

    @Test
    void create() throws Exception {
        when(this.todoService.add(any(TodoRequest.class)))
                .then( (i) -> {
                    TodoRequest request = i.getArgument(0, TodoRequest.class);
                    return new TodoEntity(this.expected.getId(),
                            request.getTitle(),
                            this.expected.getOrder(),
                            this.expected.getCompleted());

                });

        TodoRequest request = new TodoRequest();
        request.setTitle("ANY TITLE");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        this.mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("ANY TITLE"));
    }


    @Test
    void readOne() throws Exception {
        given(todoService.searchById(123L)).willReturn(expected);

        mvc.perform(get("/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.title").value(expected.getTitle()))
                .andExpect(jsonPath("$.order").value(expected.getOrder()))
                .andExpect(jsonPath("$.completed").value(expected.getCompleted()));
    }

    @Test
    void readOneException() throws Exception {
        given(todoService.searchById(123L)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mvc.perform(get("/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void readAll() throws Exception {
        List<TodoEntity> mockList = new ArrayList<>();
        int expectedLength = 10;
        for (int i = 0; i < expectedLength; i++) {
            mockList.add(mock(TodoEntity.class));
        }

        given(todoService.searchAll()).willReturn(mockList);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedLength));
    }

    @Test
    void deleteAll() throws Exception {
        mvc.perform(delete("/"))
                .andExpect(status().isOk());
    }

}
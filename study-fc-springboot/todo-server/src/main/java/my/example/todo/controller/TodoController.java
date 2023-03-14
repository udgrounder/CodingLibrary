package my.example.todo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import my.example.todo.model.TodoEntity;
import my.example.todo.model.TodoRequest;
import my.example.todo.model.TodoResponse;
import my.example.todo.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;


    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {

        log.info("CREATE");

        if (ObjectUtils.isEmpty(request.getTitle()))
            return ResponseEntity.badRequest().build();

        if( ObjectUtils.isEmpty(request.getOrder()))
            request.setOrder(0L);

        if ( ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);

        TodoEntity result = this.todoService.add(request);

        return ResponseEntity.ok(new TodoResponse(result));
    }


    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        log.info("READ ONE");

        TodoEntity todoEntity = this.todoService.searchById(id);
        return ResponseEntity.ok(new TodoResponse(todoEntity));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        log.info("READ ALL");

        List<TodoEntity> list = this.todoService.searchAll();
        List<TodoResponse> responses = list.stream().map(TodoResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update (@PathVariable Long id, @RequestBody TodoRequest request) {
        log.info("UPDATE");
        TodoEntity result = this.todoService.updateById(id, request);
        return ResponseEntity.ok(new TodoResponse((result)));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne (@PathVariable Long id) {
        log.info("DELETE");
        this.todoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll () {
        log.info("DELETE ALL");
        this.todoService.deleteAll();
        return ResponseEntity.ok().build();
    }



}

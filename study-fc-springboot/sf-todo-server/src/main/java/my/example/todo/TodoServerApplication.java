package my.example.todo;

import my.example.todo.repository.TodoRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoServerApplication {
    public static void main(String[] args) {
        System.out.println("Hello todo");
        TodoRepository repository;

        SpringApplication.run(TodoServerApplication.class, args);

    }
}

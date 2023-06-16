package my.example.restapi.server.advice;


import lombok.extern.slf4j.Slf4j;
import my.example.restapi.exception.ApiErrorResponse;
import my.example.restapi.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"my.example.restapi.server"})
public class RestApiServerExceptionAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleOpenApiException(ResourceNotFoundException ex){
        log.error("handleOpenApiException",ex);
        ApiErrorResponse response = ApiErrorResponse.failure(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getHttpStatus()));
    }
}

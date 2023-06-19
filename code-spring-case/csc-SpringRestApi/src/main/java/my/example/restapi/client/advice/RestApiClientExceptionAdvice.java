package my.example.restapi.client.advice;


import lombok.extern.slf4j.Slf4j;
import my.example.restapi.exception.ApiErrorResponse;
import my.example.restapi.exception.OpenApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"my.example.restapi.client.controller"})
public class RestApiClientExceptionAdvice {

    @ExceptionHandler(OpenApiException.class)
    public ResponseEntity<ApiErrorResponse> handleOpenApiException(OpenApiException ex){
        log.error("handleOpenApiException",ex);
        ApiErrorResponse response = ApiErrorResponse.failure(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getHttpStatus()));
    }
}

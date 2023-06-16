package my.example.restapi.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiErrorResponse {

    private final String code;
    private final String message;

    public static ApiErrorResponse failure(String code, String message) {
        return ApiErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

}
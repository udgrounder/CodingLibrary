package my.example.security.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiErrorResponse {

    private final String code;
    private final String message;

//    public static <T> ApiErrorResponse<T> failure() {
//        return ApiErrorResponse.<T>builder()
//                .code(ResponseType.FAILURE.name())
//                .message(ResponseType.FAILURE.getMessage())
//                .build();
//    }

    public static ApiErrorResponse failure(String code, String message) {
        return ApiErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

}
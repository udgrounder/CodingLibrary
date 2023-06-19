package my.example.restapi.exception;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private String code;
    private String message;

    public static ApiErrorResponse failure(String code, String message) {
        return ApiErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

}
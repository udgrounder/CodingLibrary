package my.example.restdocs.common.restapi;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class ApiResponse<T> {

    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .success(ResponseType.SUCCESS.isSuccess())
                .code(ResponseType.SUCCESS.name())
                .message(ResponseType.SUCCESS.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(ResponseType.SUCCESS.isSuccess())
                .code(ResponseType.SUCCESS.name())
                .message(ResponseType.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> failure() {
        return ApiResponse.<T>builder()
                .success(ResponseType.FAILURE.isSuccess())
                .code(ResponseType.FAILURE.name())
                .message(ResponseType.FAILURE.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> failure(String code, String message) {
        return ApiResponse.<T>builder()
                .success(ResponseType.FAILURE.isSuccess())
                .code(code)
                .message(message)
                .build();
    }

    @Getter
    @RequiredArgsConstructor
    public enum ResponseType {
        SUCCESS(true, "SUCCESS","성공"),
        FAILURE(false, "FAILURE","실패");

        private final boolean success;
        private final String code;
        private final String message;
    }

}
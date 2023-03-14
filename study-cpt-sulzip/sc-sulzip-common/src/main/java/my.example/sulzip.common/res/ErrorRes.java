package my.example.sulzip.common.res;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorRes {

    private final int status;
    private final String code;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorRes(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

package my.example.security.exception;

public class RestApiException extends RuntimeException {

    final RestApiErrorCode errorCode;
    final String message;

    private RestApiException() {
        throw new AssertionError();
    }

    public RestApiException(RestApiErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();

    }

    public RestApiException(RestApiErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;

    }

    public RestApiException(Throwable e, RestApiErrorCode errorCode) {
        super(e);
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public RestApiException(Throwable e, RestApiErrorCode errorCode, String message) {
        super(e);
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getHttpStatus() {
        return this.errorCode.getHttpStatus();
    }
    public String getCode() {
        return this.errorCode.getErrorCode();
    }
    @Override
    public String getMessage() {
        return this.message;
    }

    public RestApiErrorCode getErrorCode() {
        return this.errorCode;
    }

}

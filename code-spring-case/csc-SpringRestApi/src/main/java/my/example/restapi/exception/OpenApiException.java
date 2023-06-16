package my.example.restapi.exception;

public class OpenApiException extends RuntimeException {

    final OpenApiErrorCode errorCode;
    final String message;

    private OpenApiException () {
        throw new AssertionError();
    }

    public OpenApiException(OpenApiErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();

    }

    public OpenApiException(OpenApiErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;

    }

    public OpenApiException(Throwable e, OpenApiErrorCode errorCode) {
        super(e);
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public OpenApiException(Throwable e, OpenApiErrorCode errorCode, String message) {
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

    public OpenApiErrorCode getErrorCode() {
        return this.errorCode;
    }

}
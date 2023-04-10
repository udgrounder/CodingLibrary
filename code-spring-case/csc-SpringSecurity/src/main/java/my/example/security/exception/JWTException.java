package my.example.security.exception;


import javax.servlet.ServletException;

public class JWTException extends ServletException {

    protected RestApiErrorCode errorCode;


    private JWTException() {
        super();
    }
    public JWTException(RestApiErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
    public JWTException(String message, RestApiErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public JWTException(String message, Throwable rootCause, RestApiErrorCode errorCode) {
        super(message, rootCause);
        this.errorCode = errorCode;
    }
    public JWTException(Throwable rootCause, RestApiErrorCode errorCode) {
        super(rootCause);
        this.errorCode = errorCode;
    }
    public Throwable getRootCause() {
        return getCause();
    }

    public RestApiErrorCode getErrorCode() {
        return this.errorCode;
    }

}

package my.example.restapi.exception;

/**
 * 요청온 리소스가 없는 경우 발생한다.
 */
public class ResourceConflictException extends OpenApiException {

    public ResourceConflictException() {
        super(OpenApiErrorCode.RESOURCE_CONFLICT);
    }
}

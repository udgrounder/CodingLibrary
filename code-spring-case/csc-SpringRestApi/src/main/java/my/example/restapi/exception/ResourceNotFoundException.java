package my.example.restapi.exception;

/**
 * 요청온 리소스가 없는 경우 발생한다.
 */
public class ResourceNotFoundException extends OpenApiException {

    public ResourceNotFoundException() {
        super(OpenApiErrorCode.RESOURCE_NOT_FOUND);
    }
}

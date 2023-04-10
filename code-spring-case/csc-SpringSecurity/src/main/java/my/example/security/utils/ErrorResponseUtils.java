package my.example.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import my.example.security.exception.ApiErrorResponse;
import my.example.security.exception.RestApiErrorCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ErrorResponseUtils {

    private ErrorResponseUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void setErrorResponse(HttpServletResponse response, RestApiErrorCode errorCode){

        ObjectMapper mapper = new ObjectMapper();

        response.setStatus(errorCode.getHttpStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ApiErrorResponse errorResponse = ApiErrorResponse.failure(errorCode.getErrorCode(), errorCode.getMessage());
        try{
            String json = mapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
        }catch (IOException e){
            log.error(errorResponse.toString(), e);
        }
    }
}

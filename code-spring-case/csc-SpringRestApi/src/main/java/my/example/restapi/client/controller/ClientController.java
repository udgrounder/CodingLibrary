package my.example.restapi.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.example.restapi.client.config.ClientApiCaller;
import my.example.restapi.exception.ApiErrorResponse;
import my.example.restapi.exception.OpenApiErrorCode;
import my.example.restapi.exception.OpenApiException;
import my.example.restapi.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/restapi-client/user")
public class ClientController {

    private final ClientApiCaller clientApiCaller;
    private final ObjectMapper objectMapper;
    private final Retrofit clientApiRetrofit;
    User user = null;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public void postUser(@RequestBody User user) throws IOException {

        log.info("User : {} ", user.toString());

        Call<okhttp3.ResponseBody> responseCall = clientApiCaller.postUser(user);
        log.debug("# request postUser : {}", objectMapper.writeValueAsString(user));

        Response<okhttp3.ResponseBody> execute = responseCall.execute();
        okhttp3.ResponseBody res = execute.body();
        if ( !execute.isSuccessful() ) {
            if( HttpStatus.CONFLICT.value() == execute.code() ) {
                ApiErrorResponse errorResponse = (ApiErrorResponse) clientApiRetrofit.responseBodyConverter(ApiErrorResponse.class, ApiErrorResponse.class.getAnnotations())
                        .convert(execute.errorBody());

                log.error("errResponse : {} ", errorResponse.toString());
                throw new OpenApiException(OpenApiErrorCode.RESOURCE_CONFLICT);

            } else {

                log.error(execute.errorBody().toString());
            }
        }
        log.debug("# response getUser : {}", res);
        log.debug("# response postUser : {}", res);

    }


    @GetMapping
    public User getUser() throws IOException {

        Call<User> userCall = clientApiCaller.getUser();

        Response<User> execute = userCall.execute();
        User res = execute.body();
        if ( !execute.isSuccessful() ) {
            if( HttpStatus.NOT_FOUND.value() == execute.code() ) {
                ApiErrorResponse errorResponse = (ApiErrorResponse) clientApiRetrofit.responseBodyConverter(ApiErrorResponse.class, ApiErrorResponse.class.getAnnotations())
                        .convert(execute.errorBody());

                log.error("errResponse : {} ", errorResponse.toString());

                throw new OpenApiException(OpenApiErrorCode.RESOURCE_NOT_FOUND);

            } else {

                log.error(execute.errorBody().toString());
                throw new OpenApiException(OpenApiErrorCode.COMMON_ERROR);
            }
        }
        log.debug("# response getUser : {}", res);

        return res;
    }


    @PutMapping
    public void putUser(@RequestBody User user) throws IOException {

        Call<okhttp3.ResponseBody> responseCall = clientApiCaller.putUser(user);
        log.debug("# request putUser : {}", objectMapper.writeValueAsString(user));

        Response<okhttp3.ResponseBody> execute = responseCall.execute();
        okhttp3.ResponseBody res = execute.body();
        if ( !execute.isSuccessful() ) {
//            if( HttpStatus.NOT_FOUND.value() == execute.code() ) {
//                ApiErrorResponse errorResponse = (ApiErrorResponse) clientApiRetrofit.responseBodyConverter(ApiErrorResponse.class, ApiErrorResponse.class.getAnnotations())
//                        .convert(execute.errorBody());
//
//                log.error("errResponse : {} ", errorResponse.toString());
//
//            } else {
//
//
//            }
            log.error(execute.errorBody().toString());
            throw new OpenApiException(OpenApiErrorCode.COMMON_ERROR);
        }
        log.debug("# response putUser : {}", res);

    }

    @PatchMapping
    public void patchUser(@RequestBody User user) throws IOException {
        log.info("User : {} ", user.toString());

        Call<okhttp3.ResponseBody> responseCall = clientApiCaller.patchUser(user);
        log.debug("# request patchUser : {}", objectMapper.writeValueAsString(user));

        Response<okhttp3.ResponseBody> execute = responseCall.execute();
        okhttp3.ResponseBody res = execute.body();
        if ( !execute.isSuccessful() ) {
            if( HttpStatus.NOT_FOUND.value() == execute.code() ) {
                ApiErrorResponse errorResponse = (ApiErrorResponse) clientApiRetrofit.responseBodyConverter(ApiErrorResponse.class, ApiErrorResponse.class.getAnnotations())
                        .convert(execute.errorBody());

                log.error("errResponse : {} ", errorResponse.toString());
                throw new OpenApiException(OpenApiErrorCode.RESOURCE_NOT_FOUND);
            } else {
                log.error(execute.errorBody().toString());
                throw new OpenApiException(OpenApiErrorCode.COMMON_ERROR);
            }
        }
        log.debug("# response patchUser : {}", res);

    }


    @DeleteMapping
    public void deleteUser() throws IOException {

        Call<okhttp3.ResponseBody> responseCall = clientApiCaller.deleteUser();

        Response<okhttp3.ResponseBody> execute = responseCall.execute();
        okhttp3.ResponseBody res = execute.body();
        if ( !execute.isSuccessful() ) {
            if( HttpStatus.NOT_FOUND.value() == execute.code() ) {
                ApiErrorResponse errorResponse = (ApiErrorResponse) clientApiRetrofit.responseBodyConverter(ApiErrorResponse.class, ApiErrorResponse.class.getAnnotations())
                        .convert(execute.errorBody());

                log.error("errResponse : {} ", errorResponse.toString());
                throw new OpenApiException(OpenApiErrorCode.RESOURCE_NOT_FOUND);
            } else {
                log.error(execute.errorBody().toString());
                throw new OpenApiException(OpenApiErrorCode.COMMON_ERROR);
            }
        }
        log.debug("# response deleteUser : {}", res);

    }

}

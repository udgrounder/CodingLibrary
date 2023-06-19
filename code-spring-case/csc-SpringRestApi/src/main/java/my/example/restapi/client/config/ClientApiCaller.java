package my.example.restapi.client.config;

import my.example.restapi.client.model.ClientUser;
import my.example.restapi.model.User;
import okhttp3.ResponseBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ClientApiCaller {


    @GET("/restapi-server/user")
    Call<User> getUser();

    @POST("/restapi-server/user")
    Call<ResponseBody> postUser(@Body User user);


    @PUT("/restapi-server/user")
    Call<ResponseBody> putUser(@Body User user);

    @PATCH("/restapi-server/user")
    Call<ResponseBody> patchUser(@Body User user);

    @DELETE("/restapi-server/user")
    Call<ResponseBody> deleteUser();


    /**
     * Sample for path param and body
     */
//    @POST("/api-server/user/{userId}")
//    Call<ClientUser.Response> createUser(
//            @Path("userId") String userId,
//            @Body ClientUser.CreateRequest createRequest);

}

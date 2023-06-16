package my.example.restapi.client.config;

import my.example.restapi.client.model.ClientUser;
import my.example.restapi.model.User;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ClientApiCaller {


    @GET("/restapi-server/user")
    Call<User> getUser();

    @POST("/restapi-server/user")
    Call<ClientUser.Response> postUser(@Body User user);


    @PUT("/restapi-server/user")
    Call<ClientUser.Response> putUser(@Body User user);

    @PATCH("/restapi-server/user")
    Call<ClientUser.Response> patchUser(@Body User user);

    @DELETE("/restapi-server/user")
    Call<ClientUser.Response> deleteUser();


    /**
     * Sample for path param and body
     */
//    @POST("/api-server/user/{userId}")
//    Call<ClientUser.Response> createUser(
//            @Path("userId") String userId,
//            @Body ClientUser.CreateRequest createRequest);

}

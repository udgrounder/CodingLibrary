package my.example.restapi.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.example.restapi.client.config.ClientApiCaller;
import my.example.restapi.client.model.ClientUser;
import my.example.restapi.exception.ResourceNotFoundException;
import my.example.restapi.model.Address;
import my.example.restapi.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/restapi-client")
public class ClientController {

    private final ClientApiCaller clientApiCaller;
    private final ObjectMapper objectMapper;
    User user = null;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public void postUser(@RequestBody User user) throws IOException {

        Call<ClientUser.Response> responseCall = clientApiCaller.postUser(user);

        Response<ClientUser.Response> execute = responseCall.execute();
        ClientUser.Response body = execute.body();
        

        log.debug("# createOrderRequest: {}", objectMapper.writeValueAsString(responseCall));

        /*

        // 6. API 호출
        Call<BenepiaReservation.Response> createOrder = benepiaApiCaller.createOrder(kcpCoCd, createRequest);
        log.debug("# createOrderRequest: {}", objectMapper.writeValueAsString(createRequest));

        // 7. API 응답
        Response<BenepiaReservation.Response> execute = createOrder.execute();
        BenepiaReservation.Response createOrderResponse = execute.body();
        log.debug("# createOrderResponse: {}", createOrderResponse);
        return createOrderResponse;


         */

    }


    @GetMapping
    public User getUser() throws IOException {

        Call<User> user = clientApiCaller.getUser();

        Response<User> execute = user.execute();
        User body = execute.body();

        return body;
    }


    @PutMapping
    public void putUser(@RequestBody User user) {
        this.user = user;
    }

    @PatchMapping
    public void patchUser(@RequestBody User user) {
        if(this.user == null) {
            throw new ResourceNotFoundException();
        }
        // 여기서 수정 된것 만 반영할 쉬운 방법이 있는지 확인 해야 한다.

        // step 1. 일단 수정 또는 변경할 단위를 파악한다.
        // step 2. 요청이 리소스 깂을 Optionals(null)로 설정되어  삭제를 요청 하는지 확인 한다.
        // step 2-1. 리소스 삭제를 진행한다.
        // step 3. 해당 수정이 일부 값 변경을 요청 하는 사항 인지 확인 한다.
        // step 3-1. 기존 정보를 불러 온다.
        // step 3-2. 기존 정보에 변경된 부분만 설정 하고 업데이트 한다.

        // "step 3"을 쉽게 할 방법이 없는지 고민이 된다.

        if( user.getName() != null) {
            this.user.setName( user.getName());
        }

        if( user.getAge() != null ) {
            this.user.setAge( user.getAge());
        }

        if( user.getNickname() != null ) {
            this.user.setNickname(user.getNickname());
        }

        if( user.getAddress() != null ) {

            if( this.user.getAddress() != null ) {

                Optional<Address> address = user.getAddress();

                if (!address.isEmpty()) {

                    if(address.get().getNewAddress() != null) {
                        this.user.getAddress().get().setNewAddress(address.get().getNewAddress());
                    }

                    if(address.get().getOldAddress() != null) {
                        this.user.getAddress().get().setOldAddress(address.get().getOldAddress());
                    }
                } else {
                    // null 값을 설정하면 Optional.empty 값으로 와서 명시적으로 지워줘야 한다.
                    this.user.setAddress(null);
                }

            } else {
                this.user.setAddress(user.getAddress());
            }

        }


    }


    @DeleteMapping
    public void deleteUser() {

        if(this.user == null) {
            throw new ResourceNotFoundException();
        }

        this.user = null;

    }

}

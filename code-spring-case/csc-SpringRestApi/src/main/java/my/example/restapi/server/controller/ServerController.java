package my.example.restapi.server.controller;

import my.example.restapi.exception.ResourceConflictException;
import my.example.restapi.exception.ResourceNotFoundException;
import my.example.restapi.model.Address;
import my.example.restapi.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/restapi-server/user")
public class ServerController {

    User user = null;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public void postUser(@RequestBody User user) {

        if(this.user != null) {
            throw new ResourceConflictException();
        }
        this.user = user;

    }


    @GetMapping
    public User getUser() {

        if(this.user == null) {
            throw new ResourceNotFoundException();
        }

        return this.user;
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

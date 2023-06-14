package my.example.headConv.controller;


import my.example.headConv.model.XAuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-info")
public class UserInfoController {


    @GetMapping
    public ResponseEntity getUseInfo(@RequestHeader("x-auth-user") XAuthUser xAuthUser) {

    if( xAuthUser != null) {
        System.out.println(xAuthUser.toString());
    }

        return ResponseEntity.ok().build();
    }
}

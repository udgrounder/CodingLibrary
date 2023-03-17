package my.example.security.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/")
public class SampleSecurityController {

    private static int cnt = 1;

//    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @Secured("ROLE_USER")
    @GetMapping("/user")
    public String pageUser() {
        log.info("Call pageUser {} _{}_ ", cnt++, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "pageUser";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String pageAdmin() {
        log.info("Call pageAdmin {} _{}_ ", cnt++, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "pageAdmin";
    }


    @GetMapping("/readme")
    public String pageReadme() {
        log.info("Call pageReadme {} _{}_ ", cnt++, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "pageReadme";
    }

    @GetMapping("/readme2")
    public String pageReadme2() {
        log.info("Call pageReadme2 {} _{}_ ", cnt++, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "pageReadme2";
    }



    @GetMapping("/error")
    public String pageError() {
        log.info("Call pageError {} _{}_ ", cnt++, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "pageError";
    }

}

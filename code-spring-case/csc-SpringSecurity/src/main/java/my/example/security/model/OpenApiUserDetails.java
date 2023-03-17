package my.example.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Data
public class OpenApiUserDetails implements UserDetails {

    private static final long serialVersionUID = 4409814567533345198L;

    private String username = "Tester";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();


        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authorities;
    }
//
//
//    private static Collection<? extends GrantedAuthority> authorities(String... roles) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String role : roles) {
//            addAuthority(authorities, role);
//        }
//        return authorities;
//    }
//
//    private static Collection<? extends GrantedAuthority> authorities(List<String> permissions, String... roles) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String permission : permissions) {
//            addAuthority(authorities, permission);
//        }
//        for (String role : roles) {
//            addAuthority(authorities, role);
//        }
//        return authorities;
//    }
//
//    private static void addAuthority(List<GrantedAuthority> authorities, String role) {
//        if (role != null && !role.startsWith("ROLE_")) {
//            role = "ROLE_" + role;
//        }
//        authorities.add(new SimpleGrantedAuthority(role));
//    }





    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}

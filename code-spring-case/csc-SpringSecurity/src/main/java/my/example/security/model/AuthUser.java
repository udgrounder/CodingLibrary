package my.example.security.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthUser extends User {

    private final Info userInfo;

    public AuthUser(Info userInfo) {
        super(
                userInfo.getUsername(),
                userInfo.getPassword(),
                authorities(userInfo.getCustomersType().name())
        );
        this.userInfo = userInfo;
    }

    public AuthUser.Info getAuthUserInfo() {
        return userInfo;
    }

    private static void addAuthority(List<GrantedAuthority> authorities, String role) {
        if (role != null && !role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        authorities.add(new SimpleGrantedAuthority(role));
    }

    private static Collection<? extends GrantedAuthority> authorities(List<String> permissions, String... roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            addAuthority(authorities, permission);
        }
        for (String role : roles) {
            addAuthority(authorities, role);
        }
        return authorities;
    }

    private static Collection<? extends GrantedAuthority> authorities(String... roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            addAuthority(authorities, role);
        }
        return authorities;
    }


    @Data
    @NoArgsConstructor
    public static class Info {
        private String tokenId;

        private String username;

        private String password = "";
        private AuthUser.CustomersType customersType; // API ROLE Type
        private Long campId;
        private Long agencyId;
        private Long channelId;


        @Builder
        public Info(String tokenId, String username, String password, CustomersType customersType, Long campId, Long agencyId, Long channelId) {
            this.tokenId = tokenId;
            this.username = username;
            this.password = password;
            this.customersType = customersType;
            this.campId = campId;
            this.agencyId = agencyId;
            this.channelId = channelId;
        }
    }

    @Getter
    public enum CustomersType {
        CAMP,
        AGENCY,
        CHANNEL,
        ;
    }

}

package com.shareit.security;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;


@Getter
public class SecurityUser extends DefaultOidcUser implements UserDetails, OAuth2User {

    private final com.shareit.user.model.User user;

    private final String password;

    private final String username;


    public SecurityUser(com.shareit.user.model.User user,
                        String username,
                        String password,
                        Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities, new OidcIdToken("dummy-token", Instant.now(),
                Instant.now().plusSeconds(10), Map.of("sub", "dummy-subject")));
        this.user = user;
        this.username = username;
        this.password = password;
    }

    public SecurityUser(com.shareit.user.model.User user,
                        String username,
                        String password,
                        Collection<? extends GrantedAuthority> authorities,
                        OidcIdToken oidcIdToken
    ) {
        super(authorities, oidcIdToken);
        this.user = user;
        this.username = username;
        this.password = password;
    }


    public SecurityUser(SecurityUser securityUser,
                        OidcIdToken oidcIdToken
    ) {
        super(securityUser.getAuthorities(), oidcIdToken);
        this.user = securityUser.getUser();
        this.username = securityUser.getUsername();
        this.password = securityUser.getPassword();
    }

}

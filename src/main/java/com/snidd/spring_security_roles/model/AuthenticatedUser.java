package com.snidd.spring_security_roles.model;

import com.snidd.spring_security_roles.entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Class defines a Spring Security User.
 * Represents an authenticated user.
 */
public class AuthenticatedUser implements UserDetails {

    private User user;
    private List<GrantedAuthority> authorities;

    public AuthenticatedUser(User user, List<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    // GrantedAuthority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return  user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}

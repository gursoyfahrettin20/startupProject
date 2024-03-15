package com.ws.startupProject.configuration;

import com.ws.startupProject.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class CurrentUser implements UserDetails {

    Long id;
    String username;
    String password;
    Boolean isEnabled;
    Boolean isAdministrator;

    public CurrentUser(User user) {
        this.id = user.getId();
        this.username = user.getFirstName();
        this.password = user.getPassword();
        this.isEnabled = user.getActive();
        this.isAdministrator = user.getIsAdministrator();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
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
        return isEnabled;
    }
}

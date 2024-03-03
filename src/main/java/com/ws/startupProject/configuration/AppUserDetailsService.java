package com.ws.startupProject.configuration;

import com.ws.startupProject.user.User;
import com.ws.startupProject.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User inDB = userService.finByEmail(email);
        if (inDB == null) {
            throw new UsernameNotFoundException(email + "is not found");
        }
        return new CurrentUser(inDB);
    }
}
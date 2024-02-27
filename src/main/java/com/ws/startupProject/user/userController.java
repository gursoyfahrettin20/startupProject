package com.ws.startupProject.user;

import com.ws.startupProject.auth.token.TokenService;
import com.ws.startupProject.shared.GenericMessage;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.dto.UserCreate;
import com.ws.startupProject.user.dto.UserDTO;

import com.ws.startupProject.user.dto.UserUpdate;
import com.ws.startupProject.user.exception.AuthorizationException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class userController {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    // user creation area
    @PostMapping("/users")
    GenericMessage createUser(@Valid @RequestBody UserCreate user) {
        userService.save(user.toUser());
        String message = Messages.getMessageForLocale("website.messages.newuser", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    // User activation area
    // PatchMapping user bilgilerinin tümünü güncellemek yerine bir kısmını
    // güncellemek için kullanılır.
    @PatchMapping("/users/{token}/active")
    GenericMessage activateUser(@PathVariable String token) {
        userService.activateUser(token);
        String message = Messages.getMessageForLocale("website.messages.activate.user.success",
                LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    // User List (all user)
    @GetMapping("/users")
    public Page<UserDTO> getUser(Pageable page, @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        var loggedInUser = tokenService.VerifyToken(authorizationHeader);
        return userService.getUsers(page, loggedInUser).map(UserDTO::new);
    }

    // select id for user detail area
    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable long id) {
        return new UserDTO(userService.getUser(id));
    }

    @PutMapping("/users/{id}")
    UserDTO updateUser(@PathVariable long id, @Valid @RequestBody UserUpdate userUpdate, @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        var loggedInUser = tokenService.VerifyToken(authorizationHeader);
        if (loggedInUser.getIsAdminstrator() == false && (loggedInUser == null || loggedInUser.getId() != id) ) {
            throw new AuthorizationException();
        }
        return new UserDTO(userService.updateUser(id, userUpdate));
    }

}

package com.ws.startupProject.user;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.shared.GenericMessage;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.dto.*;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class userController {

    @Autowired
    UserService userService;

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
        String message = Messages.getMessageForLocale("website.messages.activate.user.success", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    // User List (all user)
    @GetMapping("/users")
    public Page<UserDTO> getUser(Pageable page, @AuthenticationPrincipal CurrentUser currentUser) {
        return userService.getUsers(page, currentUser).map(UserDTO::new);
    }

    // select id for user detail area
    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable long id) {
        return new UserDTO(userService.getUser(id));
    }

    // User Update
    @PutMapping("/users/{id}")
    @PreAuthorize("#id == #currentUser.id or #currentUser.isAdministrator == true")
    // SecurityConfiguration classına  EnableMethodSecurity(prePostEnabled = true) özelliği eklendiğinde
    // Busatır (PreAuthorize("#id == #currentUser.id or #currentUser.isAdministrator == true")) ile doğrulama yapılıyor.
    UserDTO updateUser(@PathVariable long id, @Valid @RequestBody UserUpdate userUpdate, @AuthenticationPrincipal CurrentUser currentUser) {
        return new UserDTO(userService.updateUser(id, userUpdate));
    }

    // User Delete
    @DeleteMapping("/users/{id}")
    @PreAuthorize("#id == #currentUser.id or #currentUser.isAdministrator == true")
    GenericMessage deleteUser(@PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Silinemez.");
        if (currentUser.getIsAdministrator() && id == currentUser.getId()) {
            return message;
        }
        userService.deleteUser(id);
        message = new GenericMessage("User is Delete");
        return message;

    }

    // User Password Reset
    @PostMapping("/users/password-reset")
    GenericMessage passwordResetRequest(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        userService.handleResetRequest(passwordResetRequest);
        return new GenericMessage("Check your email address to reset your Password");
    }

    // User Password Reset işleminden sonra user ın metodunu update etme metodu
    @PatchMapping("/users/{token}/password")
    GenericMessage setPassword(@PathVariable String token, @Valid @RequestBody PasswordUpdate passwordUpdate) {
        userService.updatePassword(token, passwordUpdate);
        return new GenericMessage("Password Update successfully");

    }
}

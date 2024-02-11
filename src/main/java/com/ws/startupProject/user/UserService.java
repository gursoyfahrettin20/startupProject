package com.ws.startupProject.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ws.startupProject.email.EmailService;
import com.ws.startupProject.user.dto.UserDTO;
import com.ws.startupProject.user.exception.ActivationNotificationException;
import com.ws.startupProject.user.exception.InvalidTokenException;
import com.ws.startupProject.user.exception.NotFoundException;
import com.ws.startupProject.user.exception.NotUniqueEmailException;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // @Transactional dataların depended olan başka şeyler varsa ve onları
    // oluştururken bir hata oluşursa
    // db ye kaydetmeyi önleyen bir class dır.
    // Aşağıda sadece MailException atarsa db ye kaydetmeme rollback örneği
    // oluşturuldu.
    @Transactional(rollbackOn = MailException.class)
    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            emailService.userSendActivationEmail(user.getEmail(), user.getActivationToken());
        } catch (DataIntegrityViolationException exception) {
            throw new NotUniqueEmailException();
        } catch (MailException mailException) {
            throw new ActivationNotificationException();
        }
    }

    public void activateUser(String token) {
        User inDB = userRepository.findByActivationToken(token);
        if (inDB == null) {
            throw new InvalidTokenException();
        }
        inDB.setActive(true);
        inDB.setActivationToken(null);
        userRepository.save(inDB);
    }

    public Page<User> getUsers(Pageable page) {
        return userRepository.findAll(page);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User finByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

package com.ws.startupProject.user;

import java.util.UUID;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.user.dto.UserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ws.startupProject.email.EmailService;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

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

    //  Kullanıcıyı aktif hale getirir.
    public void activateUser(String token) {
        User inDB = userRepository.findByActivationToken(token);
        if (inDB == null) {
            throw new InvalidTokenException();
        }
        inDB.setActive(true);
        inDB.setActivationToken(null);
        userRepository.save(inDB);
    }

    // Kullanıcıları Listeler
    public Page<User> getUsers(Pageable page, CurrentUser currentUser) {
        if (currentUser == null) {
            return userRepository.findAll(page);
        }
        return userRepository.findByIdNot(currentUser.getId(), page);

    }

    // Kullanıcı varmı diye kontrol eder, yoksa hata mesajı döner.
    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // kullanıcı emaili var mı yok mu diye kontrol eder.
    public User finByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Kullanıcı güncellemelerini yapar
    public User updateUser(long id, UserUpdate userUpdate) {
        User inDb = getUser(id);
        inDb.setUsername(userUpdate.username());
        return userRepository.save(inDb);
    }
}

package com.ws.startupProject.user;

import com.ws.startupProject.auth.token.TokenRepository;
import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.configuration.WebSiteConfigurationProperties;
import com.ws.startupProject.email.EmailService;
import com.ws.startupProject.file.FileService;
import com.ws.startupProject.user.dto.PasswordResetRequest;
import com.ws.startupProject.user.dto.PasswordUpdate;
import com.ws.startupProject.user.dto.UserUpdate;
import com.ws.startupProject.user.exception.ActivationNotificationException;
import com.ws.startupProject.user.exception.InvalidTokenException;
import com.ws.startupProject.user.exception.NotFoundException;
import com.ws.startupProject.user.exception.NotUniqueEmailException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private FileService fileService;

    @Autowired
    WebSiteConfigurationProperties properties;

    @Autowired
    TokenRepository tokenRepository;

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
        if (currentUser != null) {
            if (currentUser.getIsAdministrator()) {
                // Listede kendisi kariç tüm kullanıcıları görür
                return userRepository.findByIdNot(currentUser.getId(), page);
                // Admin yetkisi varsa listede tüm kullanıcıları görür
                // return userRepository.findAll(page);
            } else if (currentUser.getIsEnabled()) {
                // Admin yetkisi yoksa sadece listede kendisini görür
                return userRepository.findById(currentUser.getId(), page);
            }
        }
        // return userRepository.findAll(page);
        return userRepository.findByIdNot(0, page);
    }

    public List<User> getWUsers() {
        return userRepository.findAll();
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
        if (userUpdate.image() != null) {
            String filename = fileService.saveBase64StringAsFile(userUpdate.image(), properties.getStorage().getProfile(), inDb.getUsername());

            // Kullanıcı resimini güncellediğinde eski resmi silme bloğu başlangıcı

            fileService.deleteImageFolder(properties.getStorage().getProfile(), inDb.getImage());

            // Kullanıcı resimini güncellediğinde eski resmi silme bloğu sonu

            inDb.setImage(filename);
        }
        return userRepository.save(inDb);
    }

    // user silme işlemlerini yapar
    public void deleteUser(long id) {
        User inDb = getUser(id);
        if (inDb.getImage() != null) {
            fileService.deleteImageFolder(properties.getStorage().getProfile(), inDb.getImage());
        }
        userRepository.delete(inDb);
    }

    // password reset işlemleri
    public void handleResetRequest(PasswordResetRequest passwordResetRequest) {
        User inDb = finByEmail(passwordResetRequest.email());
        if (inDb == null) {
            throw new NotFoundException(0);
        }
        inDb.setPasswordResetToken(UUID.randomUUID().toString());
        this.userRepository.save(inDb);
        this.emailService.sendPasswordResetEmail(inDb.getEmail(), inDb.getPasswordResetToken());
    }

    public void updatePassword(String token, PasswordUpdate passwordUpdate) {
        User inDb = userRepository.findByPasswordResetToken(token);
        if (inDb == null) {
            throw new InvalidTokenException();
        }
        inDb.setPasswordResetToken(null);
        inDb.setPassword(passwordEncoder.encode(passwordUpdate.password()));
        inDb.setActive(true);
        inDb.setActivationToken(null);
        userRepository.save(inDb);
    }
}

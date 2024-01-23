package com.ws.startupProject.user;

import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ws.startupProject.user.exception.ActivationNotificationException;
import com.ws.startupProject.user.exception.NotUniqueEmailException;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // @Transactional dataların depended olan başka şeyler varsa ve onları oluştururken bir hata oluşursa 
    // db ye kaydetmeyi önleyen bir class dır.
    // Aşağıda sadece MailException atarsa db ye kaydetmeme rollback örneği oluşturuldu.
    @Transactional(rollbackOn = MailException.class)
    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            userSendActivationEmail(user);
        } catch (DataIntegrityViolationException exception) {
            throw new NotUniqueEmailException();
        } catch (MailException mailException) {
            throw new ActivationNotificationException();
        }
    }

    private void userSendActivationEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("fahrettingursoy@yandex.com");
        mailMessage.setTo(user.email);
        mailMessage.setSubject("ACCOUNT ACTICATON");
        mailMessage.setText("http://localhost:3434/activation/" + user.getActivationToken());
        getMailSender().send(mailMessage);
    }

    public JavaMailSender getMailSender() {
        // https://ethereal.email/create adresinden örnek mail bilgileri alındı
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost("smtp.ethereal.email");
        mailSenderImpl.setPort(587);
        mailSenderImpl.setUsername("aliya.altenwerth@ethereal.email");
        mailSenderImpl.setPassword("hr1BuPfjpDSFQt3HJv-");

        Properties properties = mailSenderImpl.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", true);
        return mailSenderImpl;
    }
}

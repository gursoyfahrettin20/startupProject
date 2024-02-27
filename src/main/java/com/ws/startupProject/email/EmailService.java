package com.ws.startupProject.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ws.startupProject.configuration.WebSiteConfigurationProperties;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    JavaMailSenderImpl mailSenderImpl;

    @Autowired
    WebSiteConfigurationProperties properties; ///* WebSiteConfigurationProperties classında çekilen setting ayarlarından örnek alınır */

    @Autowired
    MessageSource messageSource;

    @PostConstruct
    public void initialize() {
        // https://ethereal.email/create adresinden örnek mail bilgileri alındı
        this.mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(properties.getEmail().host());
        mailSenderImpl.setPort(properties.getEmail().port());
        mailSenderImpl.setUsername(properties.getEmail().username());
        mailSenderImpl.setPassword(properties.getEmail().password());

        Properties properties = mailSenderImpl.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", true);
    }

    String ActivationEmailThemplateForHtml = """
            <html>
                <body>
                    <h1>${title}</h1>
                    <a href="${url}"> ${clickHere} </a>
                </body>
            </html>
                        """;

    public void userSendActivationEmail(String email, String activationToken) {
        var activationUrl = properties.getClient().host() + "/activation/" + activationToken;
        var title = messageSource.getMessage("website.mail.user.create.title", null, LocaleContextHolder.getLocale());
        var clickHere = messageSource.getMessage("website.mail.user.create.clickHere", null, LocaleContextHolder.getLocale());

        var mailBody = ActivationEmailThemplateForHtml
                .replace("${url}", activationUrl)
                .replace("${title}", title)
                .replace("${clickHere}", clickHere);

        MimeMessage mimeMessage = mailSenderImpl.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            mailMessage.setFrom(properties.getEmail().from());
            mailMessage.setTo(email);
            mailMessage.setSubject(title);
            mailMessage.setText(mailBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        this.mailSenderImpl.send(mimeMessage);
    }
}

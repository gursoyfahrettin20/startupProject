package com.ws.startupProject.configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
///*Bu Class tamamı aplication.yml de "websiteconfiguration" bulunan congiguration dosyalarını */ 
@ConfigurationProperties(prefix = "websiteconfiguration")
public class WebSiteConfigurationProperties {
    
    private Client client;
    private Email email;

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static record Email(
            String username,
            String password,
            String host,
            Integer port,
            String from) {
    }

    public static record Client(String host) {
    }
}

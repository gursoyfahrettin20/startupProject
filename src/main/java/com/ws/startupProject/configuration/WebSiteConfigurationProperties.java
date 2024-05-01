package com.ws.startupProject.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
///*Bu Class tamamı aplication.yml de "websiteconfiguration" bulunan congiguration dosyalarını */ 
@ConfigurationProperties(prefix = "websiteconfiguration")
public class WebSiteConfigurationProperties {

    private Client client;
    private Email email;
    private Storage storage = new Storage();
    private String tokenType;

    public static record Email(
            String username,
            String password,
            String host,
            Integer port,
            String from) {
    }

    public static record Client(String host) {
    }

    @Data
    public static class Storage {
        String root = "uploads";
        String profile = "profile";
        String slider = "slider";
        String product = "product";
        String category = "category";
        String about = "about";
        String news = "news";
        String pages = "pages";
        String pdfFile = "pdfFile";
        String wordFile = "wordFile";
        String excelFile = "excelFile";
    }
}



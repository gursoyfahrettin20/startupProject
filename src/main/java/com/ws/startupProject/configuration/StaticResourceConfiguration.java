package com.ws.startupProject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Autowired
    WebSiteConfigurationProperties properties;

    //    resimlerin Cache bellekte tutulması  ve hangi klasörde olduğunu sisteme tanıtan konfigurasyon dosyası
    //    application.yml dosyasına yazmaktansa burada yazıp extra özelleştşrmek için konfigurasyon dosyası oluşturuldu
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = Paths.get(properties.getStorage().getRoot()).toAbsolutePath().toString() + "/";
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:" + path);
    }

    //Bu alan klasör var mı diye sorgu çekiyor yoksa oluşturuyor
    @Bean
    CommandLineRunner createStorageDirs() {
        return args -> {
            createFolder(Paths.get(properties.getStorage().getRoot()));
            createFolder(Paths.get(properties.getStorage().getRoot(), properties.getStorage().getProfile()));
            createFolder(Paths.get(properties.getStorage().getRoot(), properties.getStorage().getSlider()));
            createFolder(Paths.get(properties.getStorage().getRoot(), properties.getStorage().getNews()));
            createFolder(Paths.get(properties.getStorage().getRoot(), properties.getStorage().getAbout()));
            createFolder(Paths.get(properties.getStorage().getRoot(), properties.getStorage().getPages()));
            createFolder(Paths.get(properties.getStorage().getRoot(), properties.getStorage().getProduct()));
            createFolder(Paths.get(properties.getStorage().getRoot(), properties.getStorage().getExcelFile()));
            createFolder(Paths.get(properties.getStorage().getRoot(), properties.getStorage().getPdfFile()));
            createFolder(Paths.get(properties.getStorage().getRoot(), properties.getStorage().getWordFile()));
        };
    }

    //Bu alan klasör var mı diye sorgu çekiyor yoksa oluşturuyor.
    private void createFolder(Path path) {
        File file = path.toFile();
        boolean isFolderExist = file.exists() && file.isDirectory();
        if (!isFolderExist) {
            file.mkdir();
        }
    }
}

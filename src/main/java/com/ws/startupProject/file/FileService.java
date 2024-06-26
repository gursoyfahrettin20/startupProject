package com.ws.startupProject.file;

import com.ws.startupProject.configuration.WebSiteConfigurationProperties;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class FileService {
    Tika tika = new Tika();

    @Autowired
    WebSiteConfigurationProperties properties;

    String[] types;

    // Resim kayıt yeri
    public String saveBase64StringAsFile(String image, String folderName, String name) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss.SSS");
        String formatDateTime = now.format(formatter);
        String type = detectType(image);

        String fileName = name + "-" + formatDateTime + "." + type.split("/")[1];
        Path path = getProfileImagePath(folderName, fileName);
        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            outputStream.write(decodedImage(image));
            outputStream.close();
            return fileName;
        } catch (IOException e) {
            return null;
        }
    }

    public String detectType(String value) {
        return tika.detect(decodedImage(value));
    }

    private byte[] decodedImage(String encodedImage) {
        return Base64.getDecoder().decode(encodedImage.split(",")[1]);
    }

    // Birden fazla yerde kullanıldığı için tekrar tekrar yazılmaması ve kod fazlalığı olmaması açısından extract edildi
    private Path getProfileImagePath(String folderName, String fileName) {
        return Paths.get(properties.getStorage().getRoot(), folderName, fileName);
    }

    // Eğer yüklenen alanda resim varsa resmini değiştirmek istediğinde eski resmini silmesini yeni resmini eklemesini sağlıyor
    public void deleteImageFolder(String folderName, String image) {
        if (image == null) {
            return;
        }
        Path path = getImagePath(folderName, image);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Birden fazla yerde kullanıldığı için tekrar tekrar yazılmaması ve kod fazlalığı olmaması açısından extract edildi
    private Path getImagePath(String folderName, String fileName) {
        return Paths.get(properties.getStorage().getRoot(), folderName, fileName);
    }

    public String saveWordAsFile(String image, String folderName, String userName) {
        String wordFileName = userName;
        File file = new File(userName);

        Path path = Paths.get(properties.getStorage().getRoot(), folderName, wordFileName);
        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            outputStream.write(decodedImage(image));
            outputStream.close();
            return wordFileName;
        } catch (IOException e) {
            return null;
        }
    }

    public String saveExcelAsFile(String image, String folderName, String userName) {
        String excelFileName = userName;
        File file = new File(userName);

        Path path = Paths.get(properties.getStorage().getRoot(), folderName, excelFileName);
        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            outputStream.write(decodedImage(image));
            outputStream.close();
            return excelFileName;
        } catch (IOException e) {
            return null;
        }
    }

    public String savePdfAsFile(String image, String folderName, String userName) {
        String pdfFileName = userName;
        File file = new File(userName);

        Path path = Paths.get(properties.getStorage().getRoot(), folderName, pdfFileName);
        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            outputStream.write(decodedImage(image));
            outputStream.close();
            return pdfFileName;
        } catch (IOException e) {
            return null;
        }
    }


}

package com.ws.startupProject.user.dto;

import com.ws.startupProject.user.validation.FileImageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdate(
        @NotBlank(message = "{startupProject.constraint.notNull}")
        // java tarafında dil(tr-en) dosyası kullanım örneği içindir.
        @Size(min = 4, max = 255)
        String username,
        @FileImageType(types = {"jpg","jpeg", "png"})
        String image
) {
}

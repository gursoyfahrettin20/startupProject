package com.ws.startupProject.finishedWorks.dto;

import com.ws.startupProject.finishedWorksCategories.FinishedWorksCategories;
import com.ws.startupProject.finishedWorksToImage.FinishedWorksToImages;
import com.ws.startupProject.finishedWorks.FinishedWorks;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;

public record FinishedWorksCreate(
        @NotBlank(message = "Referans İsmi Boş Olamaz")
        @Size(min = 1, max = 150)
        String name,
        String url,
        String detail,
        FinishedWorksCategories finishedWorksCategories,
        FinishedWorksToImages finishedWorksToImages,
        @Size(max = 5)
        String language
) {
    public FinishedWorks toFinishedWorks() {
        FinishedWorks finishedWorks = new FinishedWorks();
        finishedWorks.setName(name);
        finishedWorks.setUrl(url);
        finishedWorks.setDetail(detail);
        finishedWorks.setFinishedWorksCategories(finishedWorksCategories);
        finishedWorks.setLanguage(language);
        finishedWorks.setFinishedWorksToImages(Collections.singletonList(finishedWorksToImages));
        return finishedWorks;
    }
}

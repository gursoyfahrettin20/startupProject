package com.ws.startupProject.finishedWorksToImage.dto;

import com.ws.startupProject.finishedWorks.FinishedWorks;
import com.ws.startupProject.finishedWorksToImage.FinishedWorksToImages;
import jakarta.validation.constraints.Size;

public record FinishedWorksToImageCreate(
        String image,

        FinishedWorks finishedWorks,

        @Size(max = 5)
        String language
) {
    public FinishedWorksToImages toFinishedWorksToImages() {
        FinishedWorksToImages finishedWorksToImages = new FinishedWorksToImages();
        finishedWorksToImages.setImage(image);
        finishedWorksToImages.setFinishedWorks(finishedWorks);
        finishedWorksToImages.setLanguage(language);
        return finishedWorksToImages;
    }
}

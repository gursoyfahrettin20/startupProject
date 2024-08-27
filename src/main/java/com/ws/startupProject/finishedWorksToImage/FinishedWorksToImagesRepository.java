package com.ws.startupProject.finishedWorksToImage;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinishedWorksToImagesRepository extends JpaRepository<FinishedWorksToImages, String> {
    List<FinishedWorksToImages> findByFinishedWorksId(String id);
}

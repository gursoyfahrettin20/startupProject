package com.ws.startupProject.finishedWorks.dto;

import com.ws.startupProject.finishedWorksCategories.FinishedWorksCategories;
import com.ws.startupProject.finishedWorks.FinishedWorks;
import lombok.Data;

@Data
public class FinishedWorksDto {
    String id;

    String name;

    String url;

    String detail;

    String language;

    FinishedWorksCategories category;

    public FinishedWorksDto(FinishedWorks finishedWorks) {
        setId(finishedWorks.getId());
        setName(finishedWorks.getName());
        setUrl(finishedWorks.getUrl());
        setDetail(finishedWorks.getDetail());
        setLanguage(finishedWorks.getLanguage());
        setCategory(finishedWorks.getFinishedWorksCategories());
    }
}

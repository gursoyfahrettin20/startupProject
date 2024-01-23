package com.ws.startupProject.error;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class ApiError {
    private int status;

    private String message;

    private String path;

    private Long timeStamp = new Date().getTime();

    private Map<String, String> validationErrors = null;
}

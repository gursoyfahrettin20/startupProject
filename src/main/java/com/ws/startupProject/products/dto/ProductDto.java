package com.ws.startupProject.products.dto;

import com.ws.startupProject.products.Products;
import lombok.Data;

@Data
public class ProductDto {
    String id;

    String name;

    String detail;

    public ProductDto(Products products) {
        setId(products.getId());
        setName(products.getName());
        setDetail(products.getDetail());
    }
}

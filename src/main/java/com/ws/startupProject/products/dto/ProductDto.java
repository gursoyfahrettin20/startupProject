package com.ws.startupProject.products.dto;

import com.ws.startupProject.categories.Categories;
import com.ws.startupProject.products.Products;
import lombok.Data;

@Data
public class ProductDto {
    String id;

    String name;

    String url;

    String detail;

    String language;

    Categories category;

    public ProductDto(Products products) {
        setId(products.getId());
        setName(products.getName());
        setUrl(products.getUrl());
        setDetail(products.getDetail());
        setLanguage(products.getLanguage());
        setCategory(products.getCategories());
    }
}

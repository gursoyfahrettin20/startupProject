package com.ws.startupProject.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ws.startupProject.categories.Categories;
import com.ws.startupProject.productToImages.ProductToImages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    
    String url;

    @Column(columnDefinition = "Text")
    String detail;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categories_id")
    Categories categories;

    @OneToMany(mappedBy = "products", cascade = CascadeType.REMOVE)
    List<ProductToImages> productToImages;


    String language;

}

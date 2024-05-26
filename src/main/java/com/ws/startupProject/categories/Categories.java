package com.ws.startupProject.categories;

import com.ws.startupProject.products.Products;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    @Lob
    String image;

    String detail;

    @OneToMany(mappedBy = "categories", cascade = CascadeType.REMOVE)
    List<Products> products;

    String language;

}

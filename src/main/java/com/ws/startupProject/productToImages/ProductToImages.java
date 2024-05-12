package com.ws.startupProject.productToImages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ws.startupProject.products.Products;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productToImages", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class ProductToImages {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Lob
    String image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="productId")
    Products products;

}

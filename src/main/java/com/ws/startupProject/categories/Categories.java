package com.ws.startupProject.categories;

import jakarta.persistence.*;
import lombok.Data;

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


}

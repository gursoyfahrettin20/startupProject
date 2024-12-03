package com.ws.startupProject.ourWeb;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ourWeb", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class OurWeb {

    @Id
    @GeneratedValue
    Long id;

    String name;

    @Column(columnDefinition = "Text")
    String image;

    @Column(columnDefinition = "Text")
    String detail;

    String language;
}

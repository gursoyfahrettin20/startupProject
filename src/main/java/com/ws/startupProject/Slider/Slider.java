package com.ws.startupProject.slider;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "slider", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class Slider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    @Column(columnDefinition = "Text")
    String image;

    @Column(columnDefinition = "Text")
    String detail;

    @Column(columnDefinition = "Text")
    String shortDetail;

    String link;

    String language;

}

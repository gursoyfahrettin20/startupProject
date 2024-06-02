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

    @Lob
    String image;

    @Lob
    String detail;

    @Lob
    String shortDetail;

    String link;

    String language;

}

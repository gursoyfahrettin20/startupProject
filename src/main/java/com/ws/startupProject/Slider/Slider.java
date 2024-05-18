package com.ws.startupProject.Slider;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    String detail;

    String shortDetail;

    String link;

}

package com.ws.startupProject.news;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="news", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class News {

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

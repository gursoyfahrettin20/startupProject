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

    @Lob
    String image;

    @Lob
    String detail;

    @Lob
    String shortDetail;

    String link;

    String language;
}

package com.ws.startupProject.finishedWorksCategories;

import com.ws.startupProject.finishedWorks.FinishedWorks;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "finishedWorksCategories", uniqueConstraints =@UniqueConstraint(columnNames = "id"))
public class FinishedWorksCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    String url;

    @Lob
    String image;

    @Lob
    String detail;

    String language;

    @OneToMany(mappedBy = "finishedWorksCategories", cascade = CascadeType.REMOVE)
    List<FinishedWorks> finishedWorks;
}

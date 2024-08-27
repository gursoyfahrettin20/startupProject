package com.ws.startupProject.finishedWorks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ws.startupProject.finishedWorksCategories.FinishedWorksCategories;
import com.ws.startupProject.finishedWorksToImage.FinishedWorksToImages;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "finishedWorks", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class FinishedWorks {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    String url;

    @Lob
    String detail;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "finishedWorksCategories_id")
    FinishedWorksCategories finishedWorksCategories;

    @OneToMany(mappedBy = "finishedWorks", cascade = CascadeType.REMOVE)
    List<FinishedWorksToImages> finishedWorksToImages;

    String language;
}

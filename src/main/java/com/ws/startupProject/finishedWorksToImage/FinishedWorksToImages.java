package com.ws.startupProject.finishedWorksToImage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ws.startupProject.finishedWorks.FinishedWorks;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "finishedWorksToImages", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class FinishedWorksToImages {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Lob
    String image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="finishedWorksId")
    FinishedWorks finishedWorks;


    String language;

}

package com.ws.startupProject.contact;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contact", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class Contact {

    @Id
    @GeneratedValue
    Long id;

    String branchName;

    String address;

    String mobilNumber;

    String branchNumber;

    String mail;

    String maps;
}

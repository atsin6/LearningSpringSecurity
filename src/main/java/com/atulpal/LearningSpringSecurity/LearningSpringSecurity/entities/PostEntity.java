package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.hibernate.envers.Audited;
//import org.hibernate.envers.NotAudited;


@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
//Hibernate needs above 2 annotations for to create entities and to convert tables to entities
@Getter @Setter

public class PostEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

//    @NotAudited
    private String description;


    //We can create our own Entity listeners with the help of below annotations
    @PrePersist
    protected void beforeSave() {

    }

    @PreUpdate
    protected void beforeUpdate() {

    }

    @PreRemove
    protected void beforeRemove() {

    }

}

package com.example.BlogApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;


}

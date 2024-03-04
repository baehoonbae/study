package com.example.postproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writer")
    private String writer;

    @Column(name = "password")
    private String password;

    @Column(name = "updateDate")
    private String updateDate;
}

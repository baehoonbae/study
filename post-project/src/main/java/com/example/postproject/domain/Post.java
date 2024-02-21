package com.example.postproject.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    private long id;
    private String title;
    private String content;

    private String writer;
    private String password;

    private String updateDate;
}

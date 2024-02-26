package com.example.postproject.service;

import com.example.postproject.domain.Post;
import com.example.postproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository2) {
        this.postRepository = postRepository2;
    }

    // 글 작성
    public void createPost(Post post) {
        postRepository.save(post);
    }

    // 글 수정
    public ResponseEntity<String> editPost(long id, String title, String texts, String password) {
        Post post = postRepository.findById(id);
        if (!post.getPassword().equals(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        post.setContent(texts);
        post.setTitle(title);

        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") );
        post.setUpdateDate(formattedDate);

        return ResponseEntity.ok("Post edited successfully");
    }

    // 글 전체 조회
    public List<Post> getAllPosts() {
       return postRepository.findAll();
    }

    // 특정 글 조회
    public Post getPostById(long id) {
        return postRepository.findById(id);
    }

    // 글 삭제
    public ResponseEntity<String> deletePost(long id, String password) {
        Post post = postRepository.findById(id);
        if (!post.getPassword().equals(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        postRepository.delete(id);
        return ResponseEntity.ok("Post edited successfully");
    }
}

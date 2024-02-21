package com.example.postproject.service;

import com.example.postproject.domain.Post;
import com.example.postproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 글 작성
    public void createPost(Post post) {
        postRepository.save(post);
    }

    // 글 수정
    public void editPost(long id, String texts, String password) {
        Post post = postRepository.findById(id);
        if (!post.getPassword().equals(password)) {
            return;
        }

        post.setContent(texts);

        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-dd-mm HH:mm:ss"));
        post.setUpdateDate(formattedDate);

        postRepository.update(id, post);
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
    public void deletePost(long id, String password) {
        Post post = postRepository.findById(id);
        if (!post.getPassword().equals(password)) {
            return;
        }

        postRepository.delete(id);
    }
}

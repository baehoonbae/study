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
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 글 작성
    public void createPost(Post post) {
        post.setViews(0);
        postRepository.save(post);
    }

    // 글 수정
    public void editPost(long id, String title, String texts) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            post.setContent(texts);
            post.setTitle(title);

            LocalDateTime now = LocalDateTime.now();
            String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            post.setUpdateDate(formattedDate);

            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다. id: " + id);
        }
    }

    // 글 전체 조회
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByIdDesc();
    }

    // 특정 글 조회
    public Optional<Post> getPostById(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setViews(post.getViews() + 1);
            postRepository.save(post);
            return optionalPost;
        } else {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다. id: " + id);
        }
    }

    // 글 삭제
    public ResponseEntity<String> deletePost(long id, String password) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (isPasswordCorrect(id, password)) {
                postRepository.delete(post);
                return ResponseEntity.ok("Post deleted successfully");
            }
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }

    }

    public boolean isPasswordCorrect(Long id, String enteredPassword) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return post.getPassword().equals(enteredPassword);
        }
        return false;
    }
}

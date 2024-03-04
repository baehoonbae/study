package com.example.postproject.controller;

import com.example.postproject.domain.Post;
import com.example.postproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = {"", "/"})
    public String home(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "home";
    }

    // Create
    @GetMapping(value = "/post/write")
    public String writePage() {
        return "write-page";
    }

    @PostMapping(value = "/post/write")
    public String writePost(Post post) {
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        post.setUpdateDate(formattedDate);

        postService.createPost(post);
        return "redirect:/";
    }

    // Read
    @GetMapping(value = "/post/{id}")
    public String showPost(@PathVariable long id, Model model) {
        Post post = postService.getPostById(id).orElse(null);
        model.addAttribute("post", post);
        return "content-page";
    }

    // Update
    @GetMapping(value = "/post/{id}/edit")
    public String updatePage(@PathVariable long id, Model model) {
        Post post = postService.getPostById(id).orElse(null);
        model.addAttribute("post", post);
        return "edit-page";
    }

    @PostMapping(value = "/post/{id}/validate-password")
    public ResponseEntity<String> validatePasswordAndRedirectToEditPage(@PathVariable long id, String password, RedirectAttributes redirectAttributes) {
        if (postService.isPasswordCorrect(id, password)) {
            redirectAttributes.addAttribute("id", id);
            return ResponseEntity.ok().body("redirect:/post/{id}/edit");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("redirect:/post/{id}"); // 비밀번호가 틀렸을 때 리다이렉트할 경로 지정
        }
    }

    @PostMapping(value = "/post/{id}/edit")
    public ResponseEntity<String> updatePost(@PathVariable long id, String title, String content, String password) {
        postService.editPost(id, title, content);
        return ResponseEntity.ok().body("edit-page");
    }

    // Delete
    @PostMapping(value = "/post/{id}/delete")
    public ResponseEntity<String> deletePost(@PathVariable long id, Post post) {
        return postService.deletePost(id, post.getPassword());
    }
}

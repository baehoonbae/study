package com.example.postproject.controller;

import com.example.postproject.domain.Post;
import com.example.postproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-dd-mm HH:mm:ss"));
        post.setUpdateDate(formattedDate);

        postService.createPost(post);
        return "redirect:/";
    }

    // Read
    @GetMapping(value = "/post/{id}")
    public String showPost(@PathVariable long id, Model model) {
        model.addAttribute("post", postService.getPostById(id));
        return "content-page";
    }

    // Update
    @PostMapping(value = "/post/{id}")
    public String updatePost(@PathVariable long id, Post post) {
        postService.editPost(id, post.getContent(), post.getPassword());
        return "redirect:/";
    }

    // Delete
    @PostMapping(value = "/post/delete/{id}")
    public String deletePost(@PathVariable long id, Post post) {
        postService.deletePost(id, post.getPassword());
        return "redirect:/";
    }
}

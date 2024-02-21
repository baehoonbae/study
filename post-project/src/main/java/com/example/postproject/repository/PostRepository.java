package com.example.postproject.repository;

import com.example.postproject.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PostRepository {
    private static Map<Long, Post> posts = new HashMap<>();
    private static long sequence = 0;

    public void save(Post post) {
        post.setId(++sequence);
        posts.put(post.getId(), post);
    }

    public void update(long id, Post post) {
        posts.put(id, post);
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    public Post findById(long id) {
        return posts.get(id);
    }

    public void delete(long id) {
        posts.remove(id);
    }
}

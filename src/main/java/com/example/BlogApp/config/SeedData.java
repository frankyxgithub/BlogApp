package com.example.BlogApp.config;

import com.example.BlogApp.model.Post;
import com.example.BlogApp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.isEmpty()){
            Post post1 = Post.builder()
                    .title("First Post")
                    .body("This is my first ever post on this blog")
                    .build();

            Post post2 = Post.builder()
                    .title("Second Post")
                    .body("This is my second post on this blog")
                    .build();

            Post post3 = Post.builder()
                    .title("Third Post")
                    .body("This is my third post on this blog")
                    .build();

            postService.save(post1);
            postService.save(post2);
            postService.save(post3);
        }
    }

}

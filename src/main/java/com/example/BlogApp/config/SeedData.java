package com.example.BlogApp.config;

import com.example.BlogApp.model.Account;
import com.example.BlogApp.model.Post;
import com.example.BlogApp.services.PostService;
import com.example.BlogApp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;


    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.isEmpty()){

            Account account1 = Account.builder()
                    .firstName("Efe")
                    .lastName("Frank")
                    .email("ef@gmail.com")
                    .password("password")
                    .build();

            Account account2 = Account.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("jd@gmail.com")
                    .password("password")
                    .build();

            Account account3 = Account.builder()
                    .firstName("Ella")
                    .lastName("Mai")
                    .email("em@gmail.com")
                    .password("password")
                    .build();

            accountService.save(account1);
            accountService.save(account2);
            accountService.save(account3);

            Post post1 = Post.builder()
                    .title("First Post")
                    .body("This is my first ever post on this blog")
                    .account(account1)
                    .build();

            Post post2 = Post.builder()
                    .title("Second Post")
                    .body("This is my second post on this blog")
                    .account(account2)
                    .build();

            Post post3 = Post.builder()
                    .title("Third Post")
                    .body("This is my third post on this blog")
                    .account(account3)
                    .build();

            postService.save(post1);
            postService.save(post2);
            postService.save(post3);
        }
    }

}

package com.example.BlogApp.config;

import com.example.BlogApp.model.Account;
import com.example.BlogApp.model.Authority;
import com.example.BlogApp.model.Post;
import com.example.BlogApp.repository.AuthorityRepository;
import com.example.BlogApp.services.PostService;
import com.example.BlogApp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;


    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.isEmpty()){

            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);

            Account account1 = Account.builder()
                    .firstName("Efe")
                    .lastName("Frank")
                    .email("ef@gmail.com")
                    .password("password")
                    .build();
            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities1::add);
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);


            Account account2 = Account.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("jd@gmail.com")
                    .password("password")
                    .build();
            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            account2.setAuthorities(authorities2);

            Account account3 = Account.builder()
                    .firstName("Ella")
                    .lastName("Mai")
                    .email("em@gmail.com")
                    .password("password")
                    .build();
            Set<Authority> authorities3 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities3::add);
            account3.setAuthorities(authorities3);

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

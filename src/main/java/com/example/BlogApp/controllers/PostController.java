package com.example.BlogApp.controllers;

import com.example.BlogApp.model.Account;
import com.example.BlogApp.model.Post;
import com.example.BlogApp.services.AccountService;
import com.example.BlogApp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model){
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post";
        }
        return "404";
    }

//    @GetMapping("/posts/new")
////    @PreAuthorize("isAuthenticated()")
//    public String createNewPost(Model model) {
//
//        Post post = new Post();
//        model.addAttribute("post", post);
//        return "post_new";
//    }

    @GetMapping("/posts/new")
    public String createNewPost(Model model){
        Optional<Account> optionalAccount = accountService.findByEmail("ef@gmail.com");
        if (optionalAccount.isPresent()){
            Post post = new Post();
            post.setAccount(optionalAccount.get());
//            Post post = Post.builder()
//                    .account(optionalAccount.get())
//                    .build();
            model.addAttribute("post", post);
            return "post_new";
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/new")
    public String saveNewPost(@ModelAttribute Post post){
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }
}

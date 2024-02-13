package com.example.BlogApp.controllers;

import com.example.BlogApp.model.Account;
import com.example.BlogApp.model.Post;
import com.example.BlogApp.services.AccountService;
import com.example.BlogApp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String createNewPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "post_new";
    }

//    @GetMapping("/posts/new")
//    public String createNewPost(Model model){
//        Optional<Account> optionalAccount = accountService.findByEmail("ef@gmail.com");
//        if (optionalAccount.isPresent()){
//            Post post = new Post();
//            post.setAccount(optionalAccount.get());
////            Post post = Post.builder()
////                    .account(optionalAccount.get())
////                    .build();
//            model.addAttribute("post", post);
//            return "post_new";
//        } else {
//            return "404";
//        }
//    }

    @PostMapping("/posts/new")
    public String saveNewPost(@ModelAttribute Post post){
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post_edit";
        } else {
            return "404";
        }
    }
    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id, Post post, BindingResult result, Model model) {

        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());

//            try {
//                fileService.save(file);
//                existingPost.setImageFilePath(file.getOriginalFilename());
//            } catch (Exception e) {
//                log.error("Error processing file: {}", file.getOriginalFilename());
//            }

            postService.save(existingPost);
        }

        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id) {
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            postService.delete(post);
            return "redirect:/";
        } else {
            return "404";
        }
    }
}

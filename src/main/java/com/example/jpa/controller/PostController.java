package com.example.jpa.controller;
import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Comment;
import com.example.jpa.model.Post;
import com.example.jpa.repository.PostRepository;
import com.example.jpa.service.CommentServiceImpl;
import com.example.jpa.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private CommentServiceImpl commentService;
    @GetMapping("/")
    public String viewHomepage(Model model){
        model.addAttribute("ListPosts", postService.getAllPosts());
        return "index";
    }
    @GetMapping("/showPost/{id}")
    public String viewPostById(Model model, @PathVariable Long id){
        model.addAttribute("post", postService.getPostId(id));
        List<Comment> comments = commentService.getAllCommentsByPostID(id);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        return "post_page";
    }
    @GetMapping("/addNewPost")
    public String showNewPostForm(Model model){
        Post post = new Post();
        model.addAttribute("post", post);
        return "new_post";
    }
    @PostMapping("/newPost")
    public String newPost(@ModelAttribute("post") Post post, @RequestParam("action") String action){
            postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/updatePost/{id}")
    public String showUpdatePostForm(@PathVariable(value="id") Long id, Model model){
        Post post = postService.getPostId(id);
        model.addAttribute("post", post);
        return "update_show";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable(value="id") Long id){
        postService.deletePostById((id));
        return "redirect:/";
    }
}

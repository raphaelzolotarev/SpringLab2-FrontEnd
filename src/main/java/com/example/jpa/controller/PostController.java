package com.example.jpa.controller;
import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Post;
import com.example.jpa.repository.PostRepository;
import com.example.jpa.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostServiceImpl postService;
    @GetMapping("/")
    public String viewHomepage(Model model){
        model.addAttribute("ListPosts", postService.getAllPosts());
        return "index";
    }
    @GetMapping("/posts/{id}")
    public String viewPostById(Model model, @PathVariable Long postId){
        model.addAttribute("ListPosts", postService.getPostId(postId));
        return "index";
    }






/*
    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
*/
    @PostMapping("/newPost")
    public String newPost(@ModelAttribute("employee") Post post, @RequestParam("action") String action){
        postRepository.save(post);
        return "redirect:/";
    }



    @PutMapping("/posts/{postId}")
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody
    Post postRequest) {
        return postRepository.findById(postId).map(post -> {
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            post.setContent(postRequest.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
    /*
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId+ " not found"));
    }*/
    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable(value="id") Long id){
        postService.deletePostById((id));
        return "redirect:/";
    }
}

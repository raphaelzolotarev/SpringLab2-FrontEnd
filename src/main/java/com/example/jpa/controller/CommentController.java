package com.example.jpa.controller;
import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Comment;
import com.example.jpa.model.Post;
import com.example.jpa.repository.CommentRepository;
import com.example.jpa.repository.PostRepository;
import com.example.jpa.service.CommentService;
import com.example.jpa.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/posts/{id}/comments")
    public String getAllCommentsByPostId(@PathVariable (value = "id") Long id, Model model) {
         model.addAttribute("comments", commentService.getAllCommentsByPostID(id));
        return "post_page";
    }

    @PostMapping("/addNewComment")
    public String createComment(@ModelAttribute("newComment") Comment comment, @RequestParam("action") String action){
        commentService.saveComment(comment);
        return "redirect:/";
    }



    /*
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable (value = "postId") Long
                                         postId, @PathVariable (value = "commentId") Long commentId, @Valid
                                 @RequestBody Comment commentRequest) {
        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not                 found");
        }
        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " +
                commentId + "not found"));
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId")
                                           Long postId, @PathVariable (value = "commentId") Long commentId) {
        return commentRepository.findByIdAndPostId(commentId,
                postId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
            // Default Exception die kan gebruikt worden is             ResponseStatusException
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found                with id " + commentId + " and postId " + postId));
    }*/
}
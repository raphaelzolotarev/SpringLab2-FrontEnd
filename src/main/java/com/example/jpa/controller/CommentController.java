package com.example.jpa.controller;
import com.example.jpa.model.Comment;
import com.example.jpa.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String createComment(@ModelAttribute("newComment") Comment comment){
        commentService.saveComment(comment);
        //eturn "redirect:/";
        return "redirect:/showPost/"+comment.getPost().getId();
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
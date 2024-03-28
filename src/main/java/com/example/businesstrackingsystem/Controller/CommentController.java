package com.example.businesstrackingsystem.Controller;

import com.example.businesstrackingsystem.Api.ApiResponse;
import com.example.businesstrackingsystem.Model.Comment;
import com.example.businesstrackingsystem.Model.Manager;
import com.example.businesstrackingsystem.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/get")
    public ResponseEntity getAllComment() {
        return ResponseEntity.ok(commentService.getAllComment());
    }

    @PostMapping("/add")
    public ResponseEntity addNewComment(@RequestBody @Valid Comment comment, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }

        Comment getComment = commentService.addComment(comment);
        return ResponseEntity.ok("Comment added successfully\n " + getComment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateComment(@PathVariable Integer id, @RequestBody @Valid Comment comment, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }

        Comment updatedComment = commentService.updateComment(id, comment);
        return ResponseEntity.ok("Comment updated successfully\n" + updatedComment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully with ID : " + id));
    }

}

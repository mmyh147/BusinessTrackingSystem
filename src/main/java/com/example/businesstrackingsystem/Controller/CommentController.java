package com.example.businesstrackingsystem.Controller;

import com.example.businesstrackingsystem.Api.ApiResponse;
import com.example.businesstrackingsystem.Model.Comment;
import com.example.businesstrackingsystem.Model.Manager;
import com.example.businesstrackingsystem.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    Logger logger = LoggerFactory.getLogger(CommentController.class);


    @GetMapping("/get")
    public ResponseEntity getAllComment() {
        logger.info("request to retrieve all comments");
        return ResponseEntity.ok(commentService.getAllComment());
    }

    @PostMapping("/add")
    public ResponseEntity addNewComment(@RequestBody @Valid Comment comment) {

        logger.info("request add user with body : " + comment);

        Comment getComment = commentService.addComment(comment);
        logger.info("add comment : " + comment);
        return ResponseEntity.ok("Comment added successfully\n " + getComment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateComment(@PathVariable Integer id, @RequestBody @Valid Comment comment) {

        logger.info("Request to update comment ID: " + id + " with comment: " + comment);
        Comment updatedComment = commentService.updateComment(id, comment);
        logger.info("comment with ID: " + id + " has been updated with: " + comment);

        return ResponseEntity.ok("Comment updated successfully\n" + updatedComment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable Integer id) {
        logger.info("Request to delete comment with ID: " + id);
        commentService.deleteComment(id);
        logger.info("comment with ID : " + id + " has been deleted");

        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully with ID : " + id));
    }

    @GetMapping("/get-by-task/{taskid}")
    public ResponseEntity getAllComment(@PathVariable Integer taskid) {
        logger.info("Request to get all comment by task ID: " + taskid);
        return ResponseEntity.ok(commentService.getAllCommentByTask(taskid));
    }


}

package com.example.businesstrackingsystem.Service;


import com.example.businesstrackingsystem.Api.ApiException;
import com.example.businesstrackingsystem.Model.Comment;
import com.example.businesstrackingsystem.Model.Task;
import com.example.businesstrackingsystem.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;

    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }


    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Integer commentId, Comment comment) {
        if (commentRepository.existsById(commentId)) {
            comment.setId(commentId);
            return commentRepository.save(comment);
        }
        throw new ApiException("Comment not found");
    }

    public void deleteComment(Integer commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ApiException("Comment not found");
        }


    }
}

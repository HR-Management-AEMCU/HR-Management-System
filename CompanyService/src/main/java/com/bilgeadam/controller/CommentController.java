package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateCommentDto;
import com.bilgeadam.repository.entity.Comment;
import com.bilgeadam.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequestMapping(COMMENT)
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(CREATE_COMMENT)
    public ResponseEntity<Boolean> createComment(String token, CreateCommentDto dto){
        return ResponseEntity.ok(commentService.createComment(token,dto));
    }

    @PostMapping(COMMENT_APPROVAL+ "/{token}")
    public ResponseEntity<Boolean> adminCommentApproval(@PathVariable String token, Long commentId, Boolean action){
        return ResponseEntity.ok(commentService.adminCommentApproval(token,commentId,action));
    }

    @PostMapping(ADD_COMPLAINT_COMMENT)
    public ResponseEntity<Boolean> addComplaint(Long commentId){
        return ResponseEntity.ok(commentService.addComplaint(commentId));
    }
    @PostMapping(CHECK_COMPLAINT_COMMENT)
    public ResponseEntity<Boolean> adminCheckComplaintMethod(String token, Long commentId, Boolean action){
        return ResponseEntity.ok(commentService.adminCheckComplaintMethod(token,commentId,action));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Comment>> getAllComments(){
        return ResponseEntity.ok(commentService.findAll());
    }
}

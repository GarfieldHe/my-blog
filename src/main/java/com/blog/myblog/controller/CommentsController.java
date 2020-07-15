package com.blog.myblog.controller;

import com.blog.myblog.dto.CommentsRes;
import com.blog.myblog.dto.GlobalMessage;
import com.blog.myblog.entity.CommentsEntity;
import com.blog.myblog.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("")
    public List<CommentsRes> getComments(String blogId) {
        return commentsService.getComments(blogId);
    }

    @PostMapping("")
    public GlobalMessage postComments(CommentsEntity commentsEntity) {
        return commentsService.postComments(commentsEntity);
    }
}

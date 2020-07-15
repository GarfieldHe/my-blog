package com.blog.myblog.dto;

import com.blog.myblog.entity.CommentsEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentsRes {
    private String id;
    private String useId;
    private String fatherId;
    private String time;
    private String blogId;
    private List<CommentsEntity> commentsEntities;
}

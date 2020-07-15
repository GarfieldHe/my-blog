package com.blog.myblog.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@Entity
@Document(collection = "blog_comments")
public class CommentsEntity {
    @Id
    private String id;
    private String useId;
    private String fatherId;
    private String time;
    private String blogId;
}

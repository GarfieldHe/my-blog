package com.blog.myblog.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@Entity
@Document(collection = "blog_file")
public class FileEntity {
    @Id
    private String id; // 文章编号
    private String fileType;
    private String time;
    private String fileClass;
    private String photoId;
    private String title;
    private String introduction;
    private int clickNum;
    private String fileInnerClass;
}

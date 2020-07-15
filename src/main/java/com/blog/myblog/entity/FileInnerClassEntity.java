package com.blog.myblog.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@Entity
@Document(collection = "blog_file_inner_class")
public class FileInnerClassEntity {
    @Id
    private String key;
    private String value;
}

package com.blog.myblog.repo;

import com.blog.myblog.entity.CommentsEntity;
import com.blog.myblog.entity.FileInnerClassEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentsRepo extends MongoRepository<CommentsEntity, String> {
    List<CommentsEntity> findByBlogIdOrderByTime(String blogId);
    List<CommentsEntity> findByBlogIdOrderByIdAsc(String blogId);
}

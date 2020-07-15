package com.blog.myblog.repo;

import com.blog.myblog.entity.FileInnerClassEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileInnerClassRepo extends MongoRepository<FileInnerClassEntity, String> {

}

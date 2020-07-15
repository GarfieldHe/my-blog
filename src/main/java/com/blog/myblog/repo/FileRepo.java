package com.blog.myblog.repo;

import com.blog.myblog.entity.FileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FileRepo extends MongoRepository<FileEntity, String> {
//    @Query(value = " { $and :[{'fileClass' : ?#{ (?0 == null) or (?0.length() == 0)  ? '{$exists:true}' :  ?0 }},"
//            + " {'fileType' : ?#{ (?1 == null) or (?1.length() == 0)  ? '{$exists:true}' : ?1 }},"
//            + "{'id' : ?#{ (?2 == null) or (?2.length() == 0)  ? '{$exists:true}' : ?2 }}] } ")
    // todo
    @Query(value = "{'fileType':?#{ (?0 == null) or (?0.length() == 0)  ? '{$exists:true}' :  ?0 }}")
    List<FileEntity> querylikesome(String fileClass, String fileType, String fileName);

    List<FileEntity> findByFileTypeAndFileClassOrderByTime(String fileType, String fileClass);
    List<FileEntity> findByFileTypeAndFileClassOrderByClickNum(String fileType, String fileClass);
    FileEntity findByFileType(String fileType);
    List<FileEntity> findByFileInnerClass(String fileInnerClass);
    List<FileEntity> findByTitleLike(String title);
}

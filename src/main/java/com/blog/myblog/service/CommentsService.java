package com.blog.myblog.service;

import com.blog.myblog.dto.CommentsRes;
import com.blog.myblog.dto.GlobalMessage;
import com.blog.myblog.entity.CommentsEntity;
import com.blog.myblog.repo.CommentsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentsService {
    @Autowired
    private CommentsRepo commentsRepo;
    public List<CommentsRes> getComments(String blogId){
        List<CommentsEntity> commentsEntities = commentsRepo.findByBlogIdOrderByIdAsc(blogId);
        Map<String, CommentsRes> commentsResMap = new HashMap<>();
        for (CommentsEntity commentsEntity:commentsEntities) {
            if (commentsEntity.getFatherId()==null) {
                CommentsRes commentsRes = CommentsRes.builder().build();
                BeanUtils.copyProperties(commentsEntity, commentsRes);
                commentsResMap.put(commentsEntity.getId(), commentsRes);
            }else {
                commentsResMap.get(commentsEntity.getFatherId()).getCommentsEntities().add(commentsEntity);
            }
        }
        List<CommentsRes> collect = commentsResMap.entrySet().stream().map(en -> {
            CommentsRes obj = CommentsRes.builder().build();
            BeanUtils.copyProperties(en, obj);
            return obj;
        }).collect(Collectors.toList());
        collect.sort(Comparator.comparing(CommentsRes::getTime).reversed());
        return collect;
    }
    public GlobalMessage postComments(CommentsEntity commentsEntity) {
        commentsRepo.save(commentsEntity);
        return GlobalMessage.builder().code("0").message("success").build();
    }
}

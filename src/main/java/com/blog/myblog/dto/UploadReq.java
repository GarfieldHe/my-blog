package com.blog.myblog.dto;

import lombok.Data;

@Data
public class UploadReq {
    private String fileName;
    private String fileClass;
    private String photoId;
    private String introduction;
    private String fileInnerClass;
}

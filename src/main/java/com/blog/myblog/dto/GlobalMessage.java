package com.blog.myblog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalMessage {
    private String code;
    private String message;
}

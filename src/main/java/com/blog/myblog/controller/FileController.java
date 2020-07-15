package com.blog.myblog.controller;

import com.blog.myblog.entity.FileEntity;
import com.blog.myblog.dto.GlobalMessage;
import com.blog.myblog.dto.UploadReq;
import com.blog.myblog.entity.FileInnerClassEntity;
import com.blog.myblog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

// 和Controller有区别，如，Controller不会定义返回类型
@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/download")
    public void downloadFile(String id, HttpServletResponse response) {
        fileService.downloadFile(id, response);
    }
    @GetMapping("/download/html")
    public String downloadHtml(String id) {
        return fileService.downloadHtml(id);
    }

    @PostMapping("/upload")
    public GlobalMessage uploadFile(@RequestParam MultipartFile file, UploadReq uploadReq) throws Exception{
        return fileService.uploadFile(file, uploadReq);
    }

    @PostMapping("/upload/img")
    public GlobalMessage uploadImg(@RequestBody MultipartFile file) throws Exception{
        return fileService.uploadImg(file);
    }

    @GetMapping("/fileList/hot")
    public List<FileEntity> fileHotList(@RequestParam(required = false) String fileType, @RequestParam(required = false) String fileClass) {
        return fileService.fileHotList(fileType, fileClass);
    }

    @GetMapping("/fileList")
    public List<FileEntity> fileList(@RequestParam(required = false) String fileType, @RequestParam(required = false) String fileClass) {
        return fileService.fileList(fileType, fileClass);
    }

    @GetMapping("/file")
    public FileEntity getFile(String id) {
        return fileService.getFile(id);
    }

    @GetMapping("/file/searchResult")
    public List<FileEntity> searchFile(String fileName) {
        return fileService.searchFile(fileName);
    }

    @GetMapping("/fileList/fileInnerClass")
    public List<FileEntity> fileListInner(String fileInnerClass) {
        return fileService.fileListInner(fileInnerClass);
    }

    @GetMapping("/fileList/fileInnerClassList")
    public List<FileInnerClassEntity> FileInnerClassList() {
        return fileService.FileInnerClassList();
    }

    @PostMapping("/file/clickNum")
    public GlobalMessage addClickNum(String id) {
        return fileService.addClickNum(id);
    }

}

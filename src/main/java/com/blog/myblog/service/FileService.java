package com.blog.myblog.service;

import com.blog.myblog.entity.FileEntity;
import com.blog.myblog.dto.GlobalMessage;
import com.blog.myblog.dto.UploadReq;
import com.blog.myblog.entity.FileInnerClassEntity;
import com.blog.myblog.repo.FileInnerClassRepo;
import com.blog.myblog.repo.FileRepo;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private FileInnerClassRepo fileInnerClassRepo;

    public String downloadHtml(String id) {
        GridFSFile fs = gridFsTemplate.findOne(Query.query(Criteria.where("filename").is(id)));
        String str = "";
        try{
            byte bs[] = new byte[1024];
            GridFsResource resource = gridFsTemplate.getResource(fs);
            InputStream inputStream = resource.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            str = buffer.toString();
        }catch (Exception e) {
            log.error("html文件转化失败"+id);
        }
        return str;
    }
    public void downloadFile(String id, HttpServletResponse response) {
        GridFSFile fs = gridFsTemplate.findOne(Query.query(Criteria.where("filename").is(id)));
        try {
            byte bs[] = new byte[1024];
            GridFsResource resource = gridFsTemplate.getResource(fs);
            InputStream inputStream = resource.getInputStream();
            ServletOutputStream outputStream = response.getOutputStream();
            FileEntity fileEntity = fileRepo.findById(id).get();
            String contentType = "";
            switch (fileEntity.getFileType()) {
                case "html":
                    contentType = "text/webviewhtml";
                    break;
                case "png":
                    contentType = "application/vnd.ms-powerpoint";
                    break;
                case "jpg":
                    contentType = "application/x-javascript";
                    break;
                case "jpeg":
                    contentType = "image/jpeg";
                    break;
                default:
                    // 抛异常
                    break;
            }
            response.setContentType(contentType);
            response.setHeader("Content-disposition", "attachment;filename=" + id + "." + fileEntity.getFileType());
            while (inputStream.read(bs) > 0) {
                outputStream.write(bs);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GlobalMessage uploadFile(MultipartFile file, UploadReq uploadReq) throws Exception {
        String contentType = "";
        System.out.println(file.getOriginalFilename().lastIndexOf("."));
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        switch (fileType) {
            case "html":
                contentType = "text/webviewhtml";
                break;
            case "png":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "jpg":
                contentType = "application/x-javascript";
                break;
            case "jpeg":
                contentType = "image/jpeg";
                break;
            default:
                // 抛异常
                break;
        }
        String id = UUID.randomUUID().toString();
        gridFsTemplate.store(file.getInputStream(), id, contentType);
        FileEntity fileEntity = FileEntity.builder().fileType(fileType).id(id).fileClass(uploadReq.getFileClass()).time(new Date().toString())
                .title(uploadReq.getFileName()).photoId(uploadReq.getPhotoId()).introduction(uploadReq.getIntroduction())
                .fileInnerClass(uploadReq.getFileInnerClass()).clickNum(0).build();
        fileRepo.save(fileEntity);
        return GlobalMessage.builder().code("0").message(id).build();
    }

    public GlobalMessage uploadImg(MultipartFile file) throws Exception {
        String contentType = "";
        System.out.println(file.getOriginalFilename().lastIndexOf("."));
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        switch (fileType) {
            case "png":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "jpg":
                contentType = "application/x-javascript";
                break;
            case "jpeg":
                contentType = "image/jpeg";
                break;
            default:
                // 抛异常
                break;
        }
        String id = UUID.randomUUID().toString();
        gridFsTemplate.store(file.getInputStream(), id, contentType);
        FileEntity fileEntity = FileEntity.builder().fileType(fileType).id(id).fileClass("image").time(new Date().toString())
                .title(id).build();
        fileRepo.save(fileEntity);
        return GlobalMessage.builder().code("0").message(id).build();
    }

    public List<FileEntity> fileList(String fileType, String fileClass) {
        return fileRepo.findByFileTypeAndFileClassOrderByTime(fileClass, fileType);
    }

    public List<FileEntity> fileHotList(String fileType, String fileClass) {
        List<FileEntity> hotFile = fileRepo.findByFileTypeAndFileClassOrderByClickNum(fileClass, fileType);
        return hotFile.subList(0, hotFile.size()>3?3:hotFile.size());
    }

    public FileEntity getFile(String id) {
        return fileRepo.findById(id).get();
    }

    public List<FileEntity> searchFile(String fileName){
        return fileRepo.findByTitleLike(fileName);
    }

    public GlobalMessage addClickNum(String id) {
        FileEntity fileEntity = fileRepo.findById(id).get();
        int num = fileEntity.getClickNum();
        fileEntity.setClickNum(num++);
        return GlobalMessage.builder().message("success").code("0").build();
    }

    public List<FileEntity> fileListInner(String fileInnerClass) {
        return fileRepo.findByFileInnerClass(fileInnerClass);
    }

    public List<FileInnerClassEntity> FileInnerClassList() {
        return fileInnerClassRepo.findAll();
    }
}

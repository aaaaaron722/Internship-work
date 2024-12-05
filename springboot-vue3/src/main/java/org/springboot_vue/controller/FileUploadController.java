package org.springboot_vue.controller;

import org.springboot_vue.utils.AliOssUtil;
import org.springboot_vue.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile file) throws Exception {
        String originFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString()+originFileName.substring(originFileName.lastIndexOf("."));
        String url = AliOssUtil.UploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}

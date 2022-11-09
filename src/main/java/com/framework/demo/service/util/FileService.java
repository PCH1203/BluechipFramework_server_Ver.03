package com.framework.demo.service.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileService {

    public ResponseEntity<?> uploadFile(MultipartFile file, HttpServletRequest request) throws IOException;
    public ResponseEntity<?> removeFile(Long fileId, String filePath, HttpServletRequest request) throws IOException;


}

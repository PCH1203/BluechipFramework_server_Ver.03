package com.framework.demo.controller;

import com.framework.demo.service.util.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/framework/api/util")
@RequiredArgsConstructor
@Tag(name = "[UTIL] Utility Management", description = "Utility")
public class UtilController {

    private final FileService fileService;

    @PostMapping(value = "/file/upload", consumes = {"multipart/form-data"})
    @Operation(description = "파일 업로드 API 입니다.", summary = "파일 업로드 API")
    public ResponseEntity<?> uploadFile(
            @RequestParam(required = true) @Parameter(description = "업로드할 파일") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        return fileService.uploadFile(file, request);
    }

    @DeleteMapping(value = "/file/remove")
    @Operation(description = "파일 삭제 API 입니다.", summary = "파일 삭제 API")
    public ResponseEntity<?> removeFile(
            @RequestParam(required = true) @Parameter(description = "FILE UUID") Long fileId,
            @RequestParam(required = true) @Parameter(description = "FILE PATH + UUID") String filePath
            , HttpServletRequest request
    ) throws IOException {
        return fileService.removeFile(fileId, filePath, request);
    }



}

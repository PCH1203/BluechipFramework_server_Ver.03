package com.framework.demo.service.util.impl;

import com.framework.demo.domain.File;
import com.framework.demo.domain.User;
import com.framework.demo.jwt.JwtTokenProvider;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.repository.util.FileRepository;
import com.framework.demo.service.util.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${file.dir}")
    private String fileDir;

    @Override
//    public ResponseEntity<?> uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
    public ResponseEntity<?> uploadFile(MultipartFile file) throws IOException {

        // request header의 userPk로 유저 정보 조회
//        User userInfo = jwtTokenProvider.findUserInfoByRequest(request);

        // 원래 파일 이름 추출.
        String origName = file.getOriginalFilename();
        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();
        // 확장자 추출
        String extension = origName.substring(origName.lastIndexOf("."));
        // uuid와 확장자 결함
        String savedName = uuid + extension;
        // 파일 저장 경로
        String savePath = fileDir + savedName;
        // upLoad 시간 설정
//        String uploadDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddsHHmmss"));

        //파일 빌더 생성
        File bcfFile = File.builder()
//                .uploader(userInfo.getUid())
                .originName(origName)
                .savedName(savedName)
                .savedPath(savePath)
//                .createDt(uploadDt)
                .build();

        // File path에 파일 복사
        file.transferTo(new java.io.File(savePath));
        //DB에 파일 명세 저장.
        File savedFile = fileRepository.save(bcfFile);

        return new ResponseEntity(new MessageResponseDto(savedFile.getId(),"파일 저장 성공"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeFile(Long fileId, String filePath, HttpServletRequest request) throws IOException {

        System.out.println("파일 삭제 IMPL");

        // request header의 userPk로 유저 정보 조회
        User userInfo = jwtTokenProvider.findUserInfoByRequest(request);

        Path savedPath = Paths.get(filePath);

        boolean isMatch = fileRepository.existsByIdAndUploader(fileId, userInfo.getUid());

                if(!isMatch) {
                    return new ResponseEntity(new MessageResponseDto(0,"파일을 찾을 수 없습니다."), HttpStatus.OK);
                }else {
                    try {
                    Files.delete(savedPath);
                    } catch (NoSuchFileException e) {
                        log.info("삭제하려는 파일/디렉토리가 없습니다.");
                        return new ResponseEntity(new MessageResponseDto(0, "삭제하려는 파일/디렉토리가 없습니다."),HttpStatus.OK);
                    }
                    System.out.println("트라이 캐치 테스트");
                    fileRepository.deleteById(fileId);

                    return new ResponseEntity(new MessageResponseDto(fileId,"파일을 삭제 했습니다."),HttpStatus.OK);
                }

    }
}

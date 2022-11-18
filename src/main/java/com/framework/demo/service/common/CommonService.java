package com.framework.demo.service.common;

import com.framework.demo.domain.*;
import com.framework.demo.jwt.JwtTokenProvider;
import com.framework.demo.mapper.auth.AuthMapper;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.agreements.dto.SaveAgreementsDto;
import com.framework.demo.model.user.vo.LoginVo;
import com.framework.demo.model.user.vo.UserVo;
import com.framework.demo.repository.agreements.AgreementsRepository;
import com.framework.demo.repository.auth.AuthRepository;
import com.framework.demo.repository.session.SessionRepository;
import com.framework.demo.repository.user.LoginRepository;
import com.framework.demo.repository.user.UserRepository;
import com.framework.demo.service.auth.AuthBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {
    private final AgreementsRepository agreementsRepository;

    public ResponseEntity<?> loadAgreements() {

        List<Agreements> agreements = agreementsRepository.findAll();

        if(agreements == null) {
            return new ResponseEntity(new MessageResponseDto(0, "데이터를 조회 할 수 없습니다."), HttpStatus.OK);
        }

        return new ResponseEntity(new MessageResponseDto(agreements, "약관 동의 내용 조회 완료"), HttpStatus.OK);
    }

    public ResponseEntity<?> saveAgreements(List<Agreements> agreementsList) {

        List<Agreements> resultList = agreementsRepository.saveAll(agreementsList);
//
        return new ResponseEntity(new MessageResponseDto(resultList, "약관 동의 저장 성공"), HttpStatus.OK);
    }






}

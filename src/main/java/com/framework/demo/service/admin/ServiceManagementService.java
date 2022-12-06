package com.framework.demo.service.admin;

import com.framework.demo.domain.ServiceCategory;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.admin.dto.ServiceAddDto;
import com.framework.demo.repository.admin.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceManagementService {

    // JPA Repository
    private final ServiceCategoryRepository serviceCategoryRepository;

    public ResponseEntity<?> serviceAdd(ServiceAddDto dto) {

        if(serviceCategoryRepository.existsByServiceId(dto.getServiceId())) {
            return new ResponseEntity(new MessageResponseDto(dto.getServiceId(), "서비스가 중복됩니다."), HttpStatus.OK);
        }

        ServiceCategory serviceCategory = ServiceCategory.builder()
                .serviceId(dto.getServiceId())
                .serviceName(dto.getServiceName())
                .isOpen(dto.getIsOpen())
                .build();

        serviceCategoryRepository.save(serviceCategory);

        return new ResponseEntity(new MessageResponseDto(serviceCategory, "서비스 등록 완료"), HttpStatus.OK);
    }

    /**
     * 서비스 목록 조회
     * @return
     */
    public ResponseEntity<?> getServiceCategory() {

        log.info("서비스 목록 조회 API (service)");

        List<ServiceCategory> serviceList = serviceCategoryRepository.findAll();

        return new ResponseEntity(new MessageResponseDto(serviceList, "서비스 목록을 조회합니다."), HttpStatus.OK);
    }






}

package com.framework.demo.service.admin;

import com.framework.demo.domain.Service;
import com.framework.demo.enums.prameter.admin.AdminEnums;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.admin.dto.ServiceAddDto;
import com.framework.demo.repository.admin.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceManagementService {

    // JPA Repository
    private final ServiceRepository serviceRepository;


    public ResponseEntity<?> serviceAdd(ServiceAddDto dto) {

        if(serviceRepository.existsByServiceId(dto.getServiceId())) {
            return new ResponseEntity(new MessageResponseDto(dto.getServiceId(), "서비스가 중복됩니다."), HttpStatus.OK);
        }

        Service service = Service.builder()
                .serviceId(dto.getServiceId())
                .serviceName(dto.getServiceName())
                .version(dto.getVersion())
                .isOpen(dto.getIsOpen())
                .build();

        serviceRepository.save(service);

        return new ResponseEntity(new MessageResponseDto(service, "서비스 등록 완료"), HttpStatus.OK);
    }

    public ResponseEntity<?> getServiceList() {

        List<Service> serviceList = serviceRepository.findAll();

        return new ResponseEntity(new MessageResponseDto(serviceList, "서비스 목록을 조회합니다."), HttpStatus.OK);
    }






}

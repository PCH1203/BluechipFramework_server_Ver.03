package com.framework.demo.service.admin;

import com.framework.demo.domain.ServiceCategory;
import com.framework.demo.enums.HttpStatusCode;
import com.framework.demo.enums.prameter.admin.AdminEnums;
import com.framework.demo.enums.prameter.admin.AdminServiceEnums;
import com.framework.demo.mapper.admin.AdminMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.admin.dto.ServiceAddDto;
import com.framework.demo.model.admin.vo.ManagementServiceVo;
import com.framework.demo.repository.admin.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

    // Mybatis Mapper
    private final AdminMapper adminMapper;


    public ResponseEntity<?> serviceAdd(ServiceAddDto dto) {

        if(serviceCategoryRepository.existsByServiceId(dto.getServiceId())) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.DUPLICATE_SERVICE, dto.getServiceId(), "서비스 ID가 중복됩니다."), HttpStatus.OK);
        }

        ServiceCategory serviceCategory = ServiceCategory.builder()
                .serviceId(dto.getServiceId())
                .serviceName(dto.getServiceName())
                .serviceUrl(dto.getServiceUrl())
                .serverPort(dto.getServerPort())
                .apiDocsPath(dto.getApiDocsPath())
                .version("1.0.0")
                .isOpen("N")
                .build();

        serviceCategoryRepository.save(serviceCategory);

        System.out.println(serviceCategory.getUuid());

        return new ResponseEntity(new MessageResponseDto(serviceCategory, "서비스 등록 완료"), HttpStatus.OK);
    }

    /**
     * 서비스 목록 조회 API
     * @return
     */
    public ResponseEntity<?> getServiceList(String searchOption, String searchValue) {

        System.out.println(">>>> 서비스 목록 조회 ");
        System.out.println("SearchOption: " + searchOption );
        System.out.println("Value: " + searchValue );

//        String option = null;
//
//        if(searchOption != null) {
//
//            option = searchOption.getStr();
//
//        }

        List<ManagementServiceVo> serviceList = adminMapper.getServiceList(searchOption, searchValue);

        if(serviceList == null || serviceList.isEmpty()) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.NOT_CONTENT, serviceList, "해당 서비스를 찾을 수 없습니다."), HttpStatus.OK);
        }

        return new ResponseEntity(new MessageResponseDto(HttpStatusCode.OK, serviceList, "서비스 목록을 조회합니다."), HttpStatus.OK);
    }

    public ResponseEntity<?> modifyServiceStatus (String serviceId) {

        System.out.println(">>>>> 서비스 상태 변경");

        ServiceCategory serviceCategory = serviceCategoryRepository.findByServiceId(serviceId);

        if (serviceCategory == null) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.NOT_FOUND, serviceId, "서비스를 찾을 수 없습니다." ), HttpStatus.OK);
        }

        if(serviceCategory.getIsOpen().equals("Y")) {
            serviceCategoryRepository.modifyIsOpenByServiceId(serviceId, "N");
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.OK,0,"서비스를 종료 하였습니다."), HttpStatus.OK);
        }

        serviceCategoryRepository.modifyIsOpenByServiceId(serviceId, "Y");

        return new ResponseEntity(new MessageResponseDto(HttpStatusCode.OK,0,"서비스를 실행 하였습니다."), HttpStatus.OK);

    }






}

package com.framework.demo.enums.prameter.admin;

import lombok.Data;
import lombok.Getter;

@Data
public class AdminServiceEnums {

    @Getter
    public enum SearchOption {

        서비스아이디("serviceId"),
        서비스명("serviceName");
        private String str;

        SearchOption(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}

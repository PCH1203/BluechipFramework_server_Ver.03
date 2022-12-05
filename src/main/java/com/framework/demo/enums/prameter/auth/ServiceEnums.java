package com.framework.demo.enums.prameter.auth;

import lombok.Data;
import lombok.Getter;

@Data
public class ServiceEnums {

    @Getter
    public enum ServiceId {

        SERVICE_01("service_01"),
        SERVICE_02("service_02"),
        SERVICE_03("service_03");
        private String str;

        ServiceId(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}

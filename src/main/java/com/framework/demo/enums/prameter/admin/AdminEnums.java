package com.framework.demo.enums.prameter.admin;

import lombok.Data;
import lombok.Getter;

@Data
public class AdminEnums {

    @Getter
    public enum SearchOption {

        name("name"),
        userEmail("userEmail"),
        phone("phone");
        private String str;

        SearchOption(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}

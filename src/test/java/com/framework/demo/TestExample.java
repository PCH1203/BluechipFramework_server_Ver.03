package com.framework.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestExample {



    @Test
    @DisplayName("기본 산수를 검증하는 테스트")
    public void testAdd() {
        assertEquals(42, Integer.sum(19,23));
    }


}

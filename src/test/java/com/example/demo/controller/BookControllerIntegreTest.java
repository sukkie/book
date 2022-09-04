package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * 통합테스(모든 빈을 IoC에 올리고 테스트)
 * WebEnvironment.MOCK 테스트용 톰캣으로 테스트
 * WebEnvironment.RANDOM_PORT 실제 톰캣으로 테스트
 * @AutoConfigureMockMvc MockMvc를 IoC에 등록해줌
 * @Transactional 은 모든 테스트 함수가 종료될 때마다 롤백해줌.
 */
@Transactional
@AutoConfigureMockMvc // MockMvc를 띄어줌
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // 통합테스트
public class BookControllerIntegreTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void save_테스트() {
        System.out.println("--------");
    }
}

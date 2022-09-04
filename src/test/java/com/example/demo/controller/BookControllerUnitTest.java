package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

// 단위테스트(Controller관련 로직만 띄우기) Filter, ControllerAdvice

@WebMvcTest // 단위테스트, Filter, ControllerAdvice와 같은 Contorller 기동에 필요한 빈들을 IoC에 띄어 줌
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    // @Mock
    @MockBean // BookService가 IoC환경에 등록됨 따라서 테스트 대상인 BookController가 IoC에서 BookService를 찾을 수 있게 됨.
    private BookService bookService;

    // BDDMockito
    @Test
    public void save_테스트() {
        // given
        System.out.println("--------");
        bookService.저장하기(new Book(1L, "title", "author"));
    }
}

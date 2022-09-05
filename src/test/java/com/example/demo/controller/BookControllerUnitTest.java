package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * IoC필요
 * 단위테스트(Controller관련 로직만 띄우기) Filter, ControllerAdvice
 */
@WebMvcTest // 단위테스트, Filter, ControllerAdvice와 같은 Contorller 기동에 필요한 빈들을 IoC에 띄어 줌
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    // @Mock
    @MockBean // BookService가 IoC환경에 등록됨 따라서 테스트 대상인 BookController가 IoC에서 BookService를 찾을 수 있게 됨.
    private BookService bookService;

    // BDDMockito
    @Test
    public void save_테스트() throws Exception {
        // given(테스트를 위한 준비)
        Book book = new Book(null, "aaa", "bbb");
        String content = new ObjectMapper().writeValueAsString(book);
        // stub
        Mockito.when(bookService.저장하기(book)).thenReturn(new Book(1L, "aaa", "bbb"));

        // when(테스트 실행)
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then(검증)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("aaa"))
                .andDo(MockMvcResultHandlers.print());
    }
}

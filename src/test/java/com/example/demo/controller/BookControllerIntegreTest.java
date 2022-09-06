package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * IoC필요.
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

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach // 테스트 메소드가 실행되기 전에 실행됨
    public void init() {
        entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @AfterEach
    public void end() {
        bookRepository.deleteAll();
    }

    @Test
    public void save_테스트() throws Exception {
        // given(테스트를 위한 준비)
        Book book = new Book(null, "aaa", "bbb");
        String content = new ObjectMapper().writeValueAsString(book);

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

    @Test
    public void findAll_테스트() throws Exception {
        // given
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(null, "aaa", "bbb"));
        bookList.add(new Book(null, "ccc", "ddd"));
        bookRepository.saveAll(bookList);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/book")
                // 응답 리소스의 기대타입 값
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("aaa"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_테스트() throws Exception {
        // given
        Long id = 1L;
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(null, "aaa", "bbb"));
        bookList.add(new Book(null, "ccc", "ddd"));
        bookRepository.saveAll(bookList);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}", id)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("aaa"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_테스트() throws Exception {
        // given
        Long id = 2L;
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(null, "aaa", "bbb"));
        bookList.add(new Book(null, "ccc", "ddd"));
        bookRepository.saveAll(bookList);

        Book book = new Book(null, "eee", "fff");
        String content = new ObjectMapper().writeValueAsString(book);

        // when(테스트 실행)
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/book/{id}", id)
                // 응답 리소스의 기대타입 값
                .accept(MediaType.APPLICATION_JSON)
                // 요청 리소스s 타입
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("eee"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delete_테스트() throws Exception {
        // given
        Long id = 2L;
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(null, "aaa", "bbb"));
        bookList.add(new Book(null, "ccc", "ddd"));
        bookRepository.saveAll(bookList);

        // when(테스트 실행)
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/book/{id}", id)
                // 응답 리소스의 기대타입 값
                .accept(MediaType.TEXT_PLAIN));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        String result = resultActions.andReturn().getResponse().getContentAsString();
        Assertions.assertEquals("ok", result);
    }
}

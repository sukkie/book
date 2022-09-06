package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * IoC불필요.
 * 단위테스트(Service와 관련된 빈들만 메모리에 띄우면 됨)
 * BookRepository => 가짜 객체로 만들 수 있음.
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {

    @InjectMocks // BookService객체가 만들어질때 해당 파일내의 @Mock으로 만들어진 빈을 BookService에 주입시켜 줌.
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void 저장하기_테스트() {
        // given
        Book book = new Book(null, "aaa", "bbb");

        // when
        Mockito.when(bookRepository.save(book)).thenReturn(new Book(1L, "aaa", "bbb"));

        // then
        Book bookEntity = bookService.저장하기(book);

        Assertions.assertEquals(book.getAuthor(), bookEntity.getAuthor());
    }
}

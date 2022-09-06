package com.example.demo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * IoC필요
 * 단위테스트 (DB 관련 빈이 IoC에 등록되면 됨)
 */
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // 가짜 디비로 테스트, Replace.NONE은 실제 디비
@DataJpaTest // BookRepository 빈을 IoC에 등록해줌
public class BookRepositoryUnitTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 저장하기_테스트() {
        // given
        Book book = new Book(null, "aaa", "bbb");

        // when
        Book bookEntity = bookRepository.save(book);

        // then
        Assertions.assertEquals(book.getAuthor(), bookEntity.getAuthor());
    }
}

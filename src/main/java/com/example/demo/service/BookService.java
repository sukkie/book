package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

// 기능을 정의할 수 있고, 트랜잰션을 관리할 수 있음
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book 저장하기(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true) // JPA의 변경감지라는 내부 기능 사용안함, 더티리드, 퍼지리드는 막을 수 있지만 팬텀리드는 못 막음
    public Book 한건가져오기(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요"));
    }

    @Transactional(readOnly = true)
    public List<Book> 모두가져오기() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book 수정하기(Long id, Book book) {
        // 더티체킹
        // entity는 영속화가됨. -> 영속성 컨텍스트에 저장됨
        Book entity = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요"));
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        return entity;
    } // 함수 종료시에 트랜잭션이 종료되고 동시에 영속화된 데이터를 디비에 갱신.(flush) -> 이걸 더티체킹이라 함.

    @Transactional
    public String 삭제하기(Long id) {
        bookRepository.deleteById(id);
        return "ok";
    }
}

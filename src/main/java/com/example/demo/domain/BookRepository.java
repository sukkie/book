package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// 원래는 @Repository를 적어야 IoC빈으로 등록이 가능한데
// JpaRepository를 상속하면 생략가능함.
// JpaRepository는 CRUD를 가지고 있음
public interface BookRepository extends JpaRepository<Book, Long> {
}

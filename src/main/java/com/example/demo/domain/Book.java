package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity  // 서버실행시에 테이블이 h2에 생성
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 데이터베이즈 번호증 전략을 따라가겠다.
    private Long id;

    private String title;

    private String author;

}

package com.example.demo;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("=========================");
        System.out.println("=========================");
    }
}

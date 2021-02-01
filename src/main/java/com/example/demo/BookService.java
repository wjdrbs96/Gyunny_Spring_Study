package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}

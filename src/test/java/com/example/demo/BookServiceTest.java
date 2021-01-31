package com.example.demo;

import org.junit.Test;

public class BookServiceTest {

    @Test
    public void save() {
        BookRepository bookRepository = new BookRepository();
        BookService bookService = new BookService(bookRepository);
    }
}
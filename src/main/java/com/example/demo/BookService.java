package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    public void setup() {
        System.out.println(bookRepository.getClass());
    }
}

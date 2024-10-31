package com.example.LibraryManagement.repository;

import com.example.LibraryManagement.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Integer id);

    List<Book> findAll();

    void update(Integer id, Book book);

    void delete(Integer id);
}

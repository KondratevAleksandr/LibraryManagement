package com.example.LibraryManagement.service;

import com.example.LibraryManagement.entity.Book;
import com.example.LibraryManagement.repository.BookRepository;
import com.example.LibraryManagement.repository.JdbcBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    public final BookRepository bookRepository;

    @Autowired
    public BookService(JdbcBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Integer id, Book bookDetails) {
        bookRepository.update(id, bookDetails);
        return bookDetails;
    }

    public void deleteBook(Integer id) {
        bookRepository.delete(id);
    }
}

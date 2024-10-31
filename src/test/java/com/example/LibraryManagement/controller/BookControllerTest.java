package com.example.LibraryManagement.controller;

import com.example.LibraryManagement.entity.Book;
import com.example.LibraryManagement.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class BookControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    private BookController bookController;

    @BeforeEach
    void setUp() {
        bookService = Mockito.mock(BookService.class);
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Title");
        book.setAuthor("Test Author");
        book.setPublicationYear(2021);

        when(bookService.getBookById(1)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"));
    }

    @Test
    void getBookById_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        when(bookService.getBookById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() throws Exception {
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Test Title 1");
        book1.setAuthor("Test Author 1");
        book1.setPublicationYear(2021);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Test Title 2");
        book2.setAuthor("Test Author 2");
        book2.setPublicationYear(2022);

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Title 1"))
                .andExpect(jsonPath("$[1].title").value("Test Title 2"));
    }

    @Test
    void createBook_ShouldReturnCreatedBook() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("New Book");
        book.setAuthor("New Author");
        book.setPublicationYear(2023);

        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content("{\"title\":\"New Book\",\"author\":\"New Author\",\"publicationYear\":2023}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Updated Book");
        book.setAuthor("Updated Author");
        book.setPublicationYear(2023);

        when(bookService.updateBook(anyInt(), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/books/1")
                        .contentType("application/json")
                        .content("{\"title\":\"Updated Book\",\"author\":\"Updated Author\",\"publicationYear\":2023}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    void deleteBook_ShouldReturnNoContent() throws Exception {
        doNothing().when(bookService).deleteBook(anyInt());

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }
}

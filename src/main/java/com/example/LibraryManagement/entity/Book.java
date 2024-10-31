package com.example.LibraryManagement.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "books")
@Data
public class Book {
    private static int counter = 0;

    private int id;
    private String title;
    private String author;
    private int publicationYear;

    public Book() {
        this.id = ++counter;
    }
}

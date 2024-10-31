package com.example.LibraryManagement.repository;

import com.example.LibraryManagement.entity.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class JdbcBookRepository implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> rowMapper = ((rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setAuthor(rs.getString("author"));
        book.setTitle(rs.getString("title"));
        book.setPublicationYear(rs.getInt("publication_year"));
        return book;
    });


    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear());
        return book;
    }

    @Override
    public Optional<Book> findById(Integer id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, rowMapper).stream().findFirst();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Integer id, Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), id);
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

package com.example.demo.bookstore;

import com.example.demo.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository <Book, Long> {
    public List<Book> findByNameContainingAndAuthorContainingAndGenreContainingIgnoreCase(String name, String author, String genre);
    public List<Book> findByQuantity(Integer quantity);
}

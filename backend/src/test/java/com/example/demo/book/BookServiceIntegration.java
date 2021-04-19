package com.example.demo.book;

import com.example.demo.TestCreationFactory;
import com.example.demo.bookstore.BookRepository;
import com.example.demo.bookstore.BookService;
import com.example.demo.bookstore.dto.BookDto;
import com.example.demo.bookstore.dto.BookMinimalDto;
import com.example.demo.bookstore.mapper.BookMapper;
import com.example.demo.bookstore.model.Book;
import com.example.demo.security.CurrentRoleService;
import com.example.demo.user.UserService;
import com.example.demo.user.model.ERole;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceIntegration {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CurrentRoleService currentRoleService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);
        Assertions.assertEquals(books.size(), bookService.findAll().size());
    }

    @Test
    void saveBook() {
        BookMinimalDto book = TestCreationFactory.newBookMinimalDto();
        bookService.saveBook(book);
        Book foundBook = bookRepository.findByQuantity(book.getQuantity()).get(0);
        Assertions.assertEquals(book.getAuthor(), foundBook.getAuthor());

    }

    @Test
    void searchBook() {
        Book book = TestCreationFactory.newBook();
        bookRepository.save(book);
        Assertions.assertEquals(book.getAuthor(),
                bookService.searchBook(book.getName(), "", "").get(0).getAuthor());
    }

    @Test
    void updateBook() {
        BookMinimalDto dto = BookMinimalDto.builder()
                .name("new name")
                .author("")
                .genre("")
                .build();
        Book book = TestCreationFactory.newBook();
        book = bookRepository.save(book);
        bookService.updateBook(book.getId(), dto);
        Assertions.assertEquals("new name",
                bookRepository.findById(book.getId()).get().getName());
    }

    @Test
    void deleteBook() {
        Book book = TestCreationFactory.newBook();
        book = bookRepository.save(book);
        bookService.deleteBook(book.getId());
        Assertions.assertFalse(bookRepository.existsById(book.getId()));
    }

    @Test
    void sellBook() {
        Book book = TestCreationFactory.newBook();
        book = bookRepository.save(book);
        bookService.sellBook(book.getId());
        Assertions.assertEquals(book.getQuantity() - 1,
                bookRepository.findById(book.getId()).get().getQuantity());
    }

}

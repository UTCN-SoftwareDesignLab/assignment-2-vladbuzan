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
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CurrentRoleService currentRoleService;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, currentRoleService, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        BookDto book = BookDto.builder().build();
        when(bookRepository.findAll()).thenReturn(books);
        BookDto dto = BookDto.builder().build();
        for(var b : books) {
            when(bookMapper.bookDtoFromBook(b)).thenReturn(dto);
        }
        Assertions.assertEquals(books.size(), bookService.findAll().size());
    }

    @Test
    void saveBook() {
        when(currentRoleService.isAdmin()).thenReturn(true);
        var bookDto = BookMinimalDto.builder().build();
        var book = Book.builder().build();
        when(bookMapper.bookFromBookMinimalDto(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        Assertions.assertDoesNotThrow(() -> bookService.saveBook(bookDto));
    }

    @Test
    void searchBook() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        BookDto dto = BookDto.builder().build();
        for(var b : books) {
            when(bookMapper.bookDtoFromBook(b)).thenReturn(dto);
        }
        when(bookRepository.findByNameContainingAndAuthorContainingAndGenreContainingIgnoreCase(
                "","", ""
        )).thenReturn(books);
        Assertions.assertEquals(books.size(),
                bookService.searchBook("", "", "").size());
    }

    @Test
    void updateBook() {
        when(currentRoleService.isAdmin()).thenReturn(true);
        BookMinimalDto request = BookMinimalDto.builder()
                .name("TEST")
                .author("")
                .genre("")
                .price(null)
                .quantity(null)
                .build();
        Book book = Book.builder()
                .id(1L)
                .build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        Assertions.assertDoesNotThrow(() -> bookService.updateBook(1L, request));
    }

    @Test
    void delete() {
        when(currentRoleService.isAdmin()).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);
        Assertions.assertDoesNotThrow(() -> bookService.deleteBook(1L));
    }

    @Test
    void sell() {
        when(currentRoleService.isRegular()).thenReturn(true);
        var book = Book.builder()
                .quantity(2)
                .build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        Assertions.assertDoesNotThrow(() -> bookService.sellBook(1L));
    }
}

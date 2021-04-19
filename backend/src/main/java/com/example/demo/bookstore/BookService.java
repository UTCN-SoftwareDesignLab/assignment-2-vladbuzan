package com.example.demo.bookstore;

import com.example.demo.bookstore.dto.BookDto;
import com.example.demo.bookstore.dto.BookMinimalDto;
import com.example.demo.bookstore.mapper.BookMapper;
import com.example.demo.security.CurrentRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CurrentRoleService currentRoleService;
    private final BookMapper bookMapper;

    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::bookDtoFromBook)
                .collect(Collectors.toList());
    }

    public void saveBook(BookMinimalDto bookMinimalDto) {
        /*if (!currentRoleService.isAdmin()) {
            throw new RuntimeException("Unauthorized");
        }*/
        var book = bookMapper.bookFromBookMinimalDto(bookMinimalDto);
        bookRepository.save(book);
    }

    public List<BookDto> searchBook(String title, String author, String genre) {
        return bookRepository.findByNameContainingAndAuthorContainingAndGenreContainingIgnoreCase(title,
                author, genre)
                .stream().map(bookMapper::bookDtoFromBook)
                .collect(Collectors.toList());
    }

    public void updateBook(Long id, BookMinimalDto request) {
       /* if (!currentRoleService.isAdmin()) {
            throw new RuntimeException("Unauthorized");
        }*/
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find book with given id"));
        if (!request.getAuthor().equals("")) {
            book.setAuthor(request.getAuthor());
        }
        if (!request.getName().equals("")) {
            book.setName(request.getName());
        }
        if (!request.getGenre().equals("")) {
            book.setGenre(request.getGenre());
        }
        if (request.getPrice() != null) {
            book.setPrice(request.getPrice());
        }
        if (request.getQuantity() != null) {
            book.setQuantity(request.getQuantity());
        }
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        /*if (!currentRoleService.isAdmin()) {
            throw new RuntimeException("Unauthorized");
        }*/
        bookRepository.deleteById(id);
    }

    public void sellBook(Long id) {
        /*if (!currentRoleService.isRegular()) {
            throw new RuntimeException("Unauthorized");
        }*/
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No book with given id"));
        if (book.getQuantity() == 0) {
            throw new RuntimeException("Book is out of stock");
        }
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
    }


}

package com.example.demo.bookstore;

import com.example.demo.bookstore.dto.BookMinimalDto;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOK)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public ResponseEntity<?> allBooks() {
        try {
            var books = bookService.findAll();
            return ResponseEntity.ok(books);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse(ex.getMessage())
            );
        }
    }

    @GetMapping(SEARCH_BOOK)
    public ResponseEntity<?> findBook(@RequestParam String title, @RequestParam String author,
                                      @RequestParam String genre) {
        return ResponseEntity.ok(bookService.searchBook(title, author, genre));
    }

    @GetMapping(EXPORT_REPORT)
    public ResponseEntity<?> export(@PathVariable ReportType type) {
        var bytes = reportServiceFactory.getReportService(type).export();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        return ResponseEntity.ok()
                .contentLength(bytes.length)
                .contentType(type.equals(ReportType.CSV) ? MediaType.TEXT_PLAIN : MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookMinimalDto book) {
        try {
            bookService.saveBook(book);
            return ResponseEntity.ok(
                    new MessageResponse("Book saved successfully")
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse(ex.getMessage())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookMinimalDto request) {
        try {
            bookService.updateBook(id, request);
            return ResponseEntity.ok(
                    new MessageResponse("Book updated successfully")
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse(ex.getMessage())
            );
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok(
                    new MessageResponse("Book removed successfully")
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse(ex.getMessage())
            );
        }
    }

    @PatchMapping(SELL_BOOK)
    public ResponseEntity<?> sellBook(@PathVariable Long id) {
        try {
            bookService.sellBook(id);
            return ResponseEntity.ok(
                    new MessageResponse("Book sold successfully")
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse(ex.getMessage())
            );
        }
    }
}

package com.example.demo.report;

import com.example.demo.bookstore.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService{

    private final BookRepository bookRepository;
    @Override
    public byte[] export() {
        return generateReport().getBytes(StandardCharsets.UTF_8);
    }

    private String generateReport() {

        var builder = new StringBuilder("ID, Title, Author, Genre, Price\n");
        var books = bookRepository.findByQuantity(0);
        for(var book : books) {
            builder.append(book.getId()).append(',')
                    .append(book.getName()).append(',')
                    .append(book.getAuthor()).append(',')
                    .append(book.getGenre()).append(',')
                    .append(book.getPrice()).append('\n');
        }
        return builder.toString();
    }

    @Override
    public ReportType getType() {
        return ReportType.CSV;
    }
}

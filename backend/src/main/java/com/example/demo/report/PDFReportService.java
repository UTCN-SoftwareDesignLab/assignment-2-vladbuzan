package com.example.demo.report;

import com.example.demo.bookstore.BookRepository;
import com.example.demo.bookstore.model.Book;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor

public class PDFReportService implements ReportService {

    private final BookRepository bookRepository;

    @Override
    public byte[] export() {

        try {
            var document = generatePDF();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();
            var inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            return inputStream.readAllBytes();
        } catch (Exception ex) {
            return new byte[0];
        }

    }


    private PDDocument generatePDF() throws IOException {
        var document = new PDDocument();
        var page = new PDPage();
        document.addPage(page);
        var stream = new PDPageContentStream(document, page);
        var books = bookRepository.findByQuantity(0);
        stream.beginText();
        //stream.setFont(PDType1Font.TIMES_ROMAN, 12);
        stream.setLeading(14.5f);
        stream.newLineAtOffset(25, 700);
        stream.showText("ID, Title, Author, Genre, Price");
        stream.newLine();
        stream.newLine();
        for (var book : books) {
            stream.showText(parseBook(book));
            stream.newLine();
        }
        stream.endText();
        stream.close();
        document.close();
        return document;
    }

    private String parseBook(Book book) {
        var sb = new StringBuilder();
        sb.append(book.getId()).append(", ")
                .append(book.getName()).append(", ")
                .append(book.getAuthor()).append(", ")
                .append(book.getGenre()).append(", ")
                .append(book.getPrice()).append(";");
        return sb.toString();
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}

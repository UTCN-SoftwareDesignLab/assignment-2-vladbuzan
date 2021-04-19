package com.example.demo.book;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.bookstore.BookController;
import com.example.demo.bookstore.BookService;
import com.example.demo.bookstore.dto.BookDto;
import com.example.demo.bookstore.dto.BookMinimalDto;
import com.example.demo.report.CSVReportService;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import com.example.demo.security.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void allBooks() throws Exception {
        List<BookDto> books = TestCreationFactory.listOf(BookDto.class);
        when(bookService.findAll()).thenReturn(books);
        ResultActions result = mockMvc.perform(get(BOOK));
        result.andExpect(status().isOk());
    }

    @Test
    void searchBook() throws Exception {
        List<BookDto> books = TestCreationFactory.listOf(BookDto.class);
        when(bookService.searchBook("", "", "")).thenReturn(books);
        ResultActions result = mockMvc.perform(get(BOOK + "/search?title=&author=&genre="));
        result.andExpect(status().isOk());
    }

    @Test
    void saveBook() throws Exception {
        BookMinimalDto book = BookMinimalDto.builder()
                .build();
        doNothing().when(bookService).saveBook(book);
        ResultActions result = performPostWithRequestBody(BOOK, book);
        result.andExpect(status().isOk());
    }

    @Test
    void updateBook() throws Exception {
        BookMinimalDto book = BookMinimalDto.builder()
                .build();
        doNothing().when(bookService).updateBook(1L, book);
        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOK + "/{id}",
                book, String.valueOf(1L));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book updated successfully")));
    }

    @Test
    void deleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);
        ResultActions result = performDeleteWIthPathVariable(BOOK + "/{id}", String.valueOf(1L));
        result.andExpect(status().isOk());
    }

    @Test
    void sellBook() throws Exception {
        doNothing().when(bookService).sellBook(1L);
        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOK + SELL_BOOK,
                null ,String.valueOf(1L));
        result.andExpect(status().isOk());
    }

    @Test
    void export() throws Exception {
        var bytes = new byte[0];
        when(reportServiceFactory.getReportService(ReportType.CSV)).thenReturn(csvReportService);
        when(csvReportService.export()).thenReturn(bytes);
        ResultActions result = performGetWithPathVariable(BOOK + EXPORT_REPORT, ReportType.CSV.name());
        result.andExpect(status().isOk());
    }

}

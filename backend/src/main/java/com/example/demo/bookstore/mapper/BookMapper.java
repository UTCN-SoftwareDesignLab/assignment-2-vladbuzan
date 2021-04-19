package com.example.demo.bookstore.mapper;

import com.example.demo.bookstore.dto.BookDto;
import com.example.demo.bookstore.dto.BookMinimalDto;
import com.example.demo.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMinimalDto bookMinimalFromBook(Book book);
    BookDto bookDtoFromBook(Book book);
    Book bookFromBookMinimalDto(BookMinimalDto book);


}

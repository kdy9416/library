package com.example.library.controller;

import com.example.library.dto.SearchDto;
import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookRepositoryCustom;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.library.dto.BookDto.*;
import static com.example.library.dto.SearchDto.*;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @PostMapping("/")
    public Book insert(@RequestBody @Valid BookRequestDto bookRequestDto) {

        Book book = Book.builder()
                    .bookName(bookRequestDto.getBookName())
                    .build();
        return bookRepository.save(book);
    }

    @GetMapping("/")
    public List<BookReponseDto> search(@RequestBody @Valid BookSearchDto bookSearchDto) {
        return bookRepository.search(bookSearchDto);
    }

    @GetMapping("/page")
    public Page<BookReponseDto> searchPageSimple(@RequestBody @Valid BookSearchDto bookSearchDto, Pageable pageable) {
        return bookRepository.searchPageSimple(bookSearchDto, pageable);
    }

}

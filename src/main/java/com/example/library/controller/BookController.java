package com.example.library.controller;

import com.example.library.dto.SearchDto;
import com.example.library.entity.Book;
import com.example.library.service.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.example.library.service.BookService;

import java.util.List;

import static com.example.library.dto.BookDto.*;
import static com.example.library.dto.SearchDto.*;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final IBookService bookService;

    @PostMapping("/")
    public InsertBookResponseDto insertBook(@RequestBody @Valid InsertBookRequestDto insertBookRequestDto) {

        return bookService.save(insertBookRequestDto);
    }

    @PutMapping("/{bookId}")
    public UpdateBookResponseDto updateBook(@PathVariable("bookId") Long bookId, @RequestBody @Valid UpdateBookRequestDto updateBookRequestDto) {

        return bookService.update(bookId, updateBookRequestDto);
    }

    @DeleteMapping("/{bookId}")
    public DeleteBookResponseDto deleteBook(@PathVariable("bookId") Long bookId){
        return bookService.delete(bookId);
    }

    @GetMapping("/{bookId}")
    public BookReponseDto findBook(@PathVariable("bookId") Long bookId) {
        return bookService.find(bookId);
    }

    @GetMapping("/list")
    public List<BookReponseDto> searchBookList(@RequestBody @Valid BookSearchDto bookSearchDto) {
        return bookService.search(bookSearchDto);
    }

    @GetMapping("/page")
    public Page<BookReponseDto> searchPageSimple(@RequestBody @Valid BookSearchDto bookSearchDto, Pageable pageable) {
        return bookService.searchPageSimple(bookSearchDto, pageable);
    }


}

package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.SearchDto;
import com.example.library.entity.Book;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.example.library.dto.BookDto.*;
import static com.example.library.dto.SearchDto.*;

public interface IBookService {

    public InsertBookResponseDto save(InsertBookRequestDto insertBookRequestDto);

    public UpdateBookResponseDto update(Long bookId, UpdateBookRequestDto updateBookRequestDtos);

    public List<BookReponseDto> search(@RequestBody @Valid BookSearchDto bookSearchDto);

    public Page<BookReponseDto> searchPageSimple(@RequestBody @Valid BookSearchDto bookSearchDto, Pageable pageable);

    public DeleteBookResponseDto delete(Long bookId);

    BookReponseDto find(Long bookId);
}

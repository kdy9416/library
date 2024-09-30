package com.example.library.repository;

import com.example.library.dto.BookDto;
import com.example.library.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.library.dto.BookDto.*;
import static com.example.library.dto.SearchDto.*;

public interface BookRepositoryCustom {

    List<BookReponseDto> search(BookSearchDto bookSearchDto);

    Page<BookReponseDto> searchPageSimple(BookSearchDto bookSearchDto, Pageable pageable);
}

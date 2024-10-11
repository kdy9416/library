package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.SearchDto;
import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.library.dto.BookDto.*;
import static com.example.library.dto.SearchDto.*;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public InsertBookResponseDto save(InsertBookRequestDto insertBookRequestDto) {

        Book book = Book.builder()
                .bookName(insertBookRequestDto.getBookName())
                .build();

        Book saveBook = bookRepository.save(book);

        return InsertBookResponseDto.builder()
                .bookId(saveBook.getBookId())
                .build();

    }

    @Override
    @Transactional
    public UpdateBookResponseDto update(Long bookId, UpdateBookRequestDto updateBookRequestDto) {

        Book findBook = bookRepository.findById(bookId).orElseThrow(()-> new IllegalArgumentException("No data exists."));
        findBook.update(updateBookRequestDto.getBookName());

        return UpdateBookResponseDto.builder()
                .bookId(bookId)
                .build();
    }

    @Override
    public List<BookReponseDto> search(BookSearchDto bookSearchDto) {
        return bookRepository.search(bookSearchDto);
    }

    @Override
    public Page<BookReponseDto> searchPageSimple(BookSearchDto bookSearchDto, Pageable pageable) {
        return bookRepository.searchPageSimple(bookSearchDto, pageable);
    }

    @Override
    public DeleteBookResponseDto delete(Long bookId) {

        bookRepository.deleteById(bookId);

        return DeleteBookResponseDto.builder()
                .bookId(bookId)
                .build();
    }

    @Override
    public BookReponseDto find(Long bookId) {

        Book findBook = bookRepository.findById(bookId).orElseThrow(()-> new IllegalArgumentException("No data exists. id=" + bookId));

        return BookReponseDto.builder()
                .bookId(findBook.getBookId())
                .bookName(findBook.getBookName())
                .build();
    }

}

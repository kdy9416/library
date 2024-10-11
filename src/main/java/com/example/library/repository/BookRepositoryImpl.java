package com.example.library.repository;

import com.example.library.dto.BookDto;
import com.example.library.dto.QBookDto_BookReponseDto;
import com.example.library.dto.SearchDto;
import com.example.library.entity.QBook;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.library.dto.BookDto.*;
import static com.example.library.dto.SearchDto.*;
import static com.example.library.entity.QBook.*;
import static org.springframework.util.StringUtils.*;

public class BookRepositoryImpl implements BookRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BookRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BookReponseDto> search(BookSearchDto bookSearchDto) {
        return queryFactory
                .select(new QBookDto_BookReponseDto(
                        book.bookId,
                        book.bookName
                ))
                .from(book)
                .where(
                        bookNameEq(bookSearchDto.getBookName())
                )
                .fetch();
    }

    @Override
    public Page<BookReponseDto> searchPageSimple(BookSearchDto bookSearchDto, Pageable pageable) {

        QueryResults<BookReponseDto> results = queryFactory
                .select(new QBookDto_BookReponseDto(
                        book.bookId,
                        book.bookName
                ))
                .from(book)
                .where(
                        bookNameEq(bookSearchDto.getBookName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BookReponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }


    private BooleanExpression bookNameEq(String bookName) {
        return isEmpty(bookName) ? null : book.bookName.eq(bookName);
    }
}


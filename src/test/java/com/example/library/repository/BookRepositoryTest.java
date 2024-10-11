package com.example.library.repository;

import com.example.library.dto.BookDto;
import com.example.library.dto.SearchDto;
import com.example.library.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.example.library.dto.BookDto.*;
import static com.example.library.dto.SearchDto.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// 프로젝트에서 설정한 DB를 사용하고 싶은 경우 다음과 같이 설정
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// test profile 설정 기반으로 테스트 동작 설정
//@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void save() {
        // given
        Book book = Book.builder()
                .bookName("노인과 바다")
                .build();

        // when
        Book saveBook = bookRepository.save(book);

        // then
        assertThat(book).isEqualTo(saveBook);
    }

    @Test
    void update() {
        // given
        Book book = Book.builder()
                .bookName("노인과 바다")
                .build();

        // when
        Book saveBook = bookRepository.save(book);
        saveBook.update("노인과 바다2");

        // then
        Book book1 = bookRepository.findByBookName("노인과 바다2");
        assertThat(book1.getBookName()).isEqualTo("노인과 바다2");
    }

    @Test
    void findById() {
        // given
        Book book = Book.builder()
                .bookName("노인과 바다")
                .build();

        Book saveBook = bookRepository.save(book);

        // when
        Book findBook = bookRepository.findById(saveBook.getBookId()).get();

        // then
        assertThat(findBook.getBookName()).isEqualTo("노인과 바다");

    }

    @Test
    void search() {

        // given
        Book book1 = Book.builder()
                .bookName("노인과 바다")
                .build();

        Book book2 = Book.builder()
                .bookName("노인과 바다 2")
                .build();

        Book book3 = Book.builder()
                .bookName("노인과 바다 3")
                .build();


        BookSearchDto bookSearchDto = BookSearchDto.builder()
                .bookName("노인과 바다 2")
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        // when
        List<BookReponseDto> result = bookRepository.search(bookSearchDto);


        // then
        assertThat(result.get(0).getBookName()).isEqualTo("노인과 바다 2");
    }

    @Test
    void searchPageSimple() {

        // given
        Book book1 = Book.builder()
                .bookName("노인과 바다")
                .build();

        Book book2 = Book.builder()
                .bookName("노인과 바다")
                .build();

        Book book3 = Book.builder()
                .bookName("메이즈 러너")
                .build();

        Book book4 = Book.builder()
                .bookName("메이즈 러너")
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);

        // when
        BookSearchDto bookSearchDto = BookSearchDto.builder()
                .bookName("노인과 바다")
                .build();

        PageRequest pageable = PageRequest.of(0, 2);

        Page<BookReponseDto> result = bookRepository.searchPageSimple(bookSearchDto, pageable);

        // then
        assertThat(result).hasSize(2);
    }


}
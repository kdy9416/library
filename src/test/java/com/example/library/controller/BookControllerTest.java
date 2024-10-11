package com.example.library.controller;

import com.example.library.BaseIntegrationTest;
import com.example.library.dto.BookDto;
import com.example.library.dto.SearchDto;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.service.IBookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.library.dto.BookDto.*;
import static com.example.library.dto.SearchDto.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class BookControllerTest extends BaseIntegrationTest {

    @Autowired
    IBookService bookService;


    @BeforeAll
    public void setUpForSelect(){

        InsertBookRequestDto testBook1 = InsertBookRequestDto.builder()
                .bookName("testBook1")
                .build();

        InsertBookRequestDto testBook2 = InsertBookRequestDto.builder()
                .bookName("testBook1")
                .build();

        InsertBookRequestDto testBook3 = InsertBookRequestDto.builder()
                .bookName("testBook1")
                .build();

        InsertBookRequestDto testBook4 = InsertBookRequestDto.builder()
                .bookName("testBook2")
                .build();

        InsertBookRequestDto testBook5 = InsertBookRequestDto.builder()
                .bookName("testBook2")
                .build();

        InsertBookRequestDto testBook6 = InsertBookRequestDto.builder()
                .bookName("testBook2")
                .build();

        bookService.save(testBook1);
        bookService.save(testBook2);
        bookService.save(testBook3);
        bookService.save(testBook4);
        bookService.save(testBook5);
        bookService.save(testBook6);

    }

    @Test
    void insertBook() throws Exception{

        // given
        InsertBookRequestDto insertBookRequestDto = InsertBookRequestDto.builder()
                .bookName("insert")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insertBookRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId", is(notNullValue())));
    }

    @Test
    void updateBook() throws Exception{

        // given
        Long bookId = saveBook("insert");

        UpdateBookRequestDto updateBookRequestDto = UpdateBookRequestDto.builder()
                .bookName("update")
                .build();

        // when
        ResultActions resultActions = mvc.perform(put("/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBookRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(bookId));

    }

    @Test
    void deleteBook() throws Exception{

        // given
        Long bookId = saveBook("insert");

        // when
        ResultActions resultActions = mvc.perform(delete("/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(bookId));
    }

    @Test
    void findBook() throws Exception{

        // given
        Long bookId = saveBook("insert");

        // when
        ResultActions resultActions = mvc.perform(get("/{bookId}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(bookId))
                .andExpect(jsonPath("$.bookName").value("insert"));
    }

    @Test
    void searchBookList() throws Exception{

        // given
        BookSearchDto bookSearchDto = BookSearchDto.builder()
                .bookName("testBook1")
                .build();

        // when
        ResultActions resultActions = mvc.perform(get("/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookSearchDto))
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void searchPageSimple() throws Exception{

        // given
        BookSearchDto bookSearchDto = BookSearchDto.builder()
                .bookName("testBook1")
                .build();

        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        ResultActions resultActions = mvc.perform(get("/page")
                        .param("page", String.valueOf(pageRequest.getPageNumber()))
                        .param("size", String.valueOf(pageRequest.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookSearchDto))
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].bookId", is(notNullValue())))
                .andExpect(jsonPath("$.content[0].bookName").value("testBook1"))
                .andExpect(jsonPath("$.content[1].bookId", is(notNullValue())))
                .andExpect(jsonPath("$.content[1].bookName").value("testBook1"));
    }

    public Long saveBook(String bookName) {

        InsertBookRequestDto book = InsertBookRequestDto.builder()
                .bookName(bookName)
                .build();

        return bookService.save(book).getBookId();
    }

}




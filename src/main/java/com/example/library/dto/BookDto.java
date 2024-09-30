package com.example.library.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BookDto {

    @Data
    @NoArgsConstructor
    public static class BookRequestDto{

        @NotBlank(message = "bookName must not be empty")
        private String bookName;

        @Builder
        public BookRequestDto(String bookName){
            this.bookName = bookName;
        }
    }

    @Data
    @NoArgsConstructor
    public static class BookReponseDto{

        private String bookName;

        @Builder
        @QueryProjection
        public BookReponseDto(String bookName){
            this.bookName = bookName;
        }
    }
}

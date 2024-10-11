package com.example.library.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BookDto {

    @Data
    @NoArgsConstructor
    public static class InsertBookRequestDto{

        @NotBlank(message = "bookName must not be empty")
        private String bookName;

        @Builder
        public InsertBookRequestDto(String bookName){
            this.bookName = bookName;
        }
    }

    @Data
    @NoArgsConstructor
    public static class InsertBookResponseDto{

        private Long bookId;

        @Builder
        public InsertBookResponseDto(Long bookId){
            this.bookId = bookId;
        }
    }

    @Data
    @NoArgsConstructor
    public static class BookReponseDto{

        private Long bookId;
        private String bookName;

        @Builder
        @QueryProjection
        public BookReponseDto(Long bookId, String bookName){
            this.bookId = bookId;
            this.bookName = bookName;
        }
    }

    @Data
    @NoArgsConstructor
    public static class UpdateBookRequestDto{

        @NotBlank(message = "bookName must not be empty")
        private String bookName;

        @Builder
        public UpdateBookRequestDto(String bookName){
            this.bookName = bookName;
        }
    }

    @Data
    @NoArgsConstructor
    public static class UpdateBookResponseDto{

        private Long bookId;

        @Builder
        public UpdateBookResponseDto(Long bookId){
            this.bookId = bookId;
        }
    }

    @Data
    @NoArgsConstructor
    public static class DeleteBookResponseDto{

        private Long bookId;

        @Builder
        public DeleteBookResponseDto(Long bookId){
            this.bookId = bookId;
        }
    }
}

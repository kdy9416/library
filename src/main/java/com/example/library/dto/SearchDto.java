package com.example.library.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

public class SearchDto {

    @Data
    @NoArgsConstructor
    public static class BookSearchDto{

        private String bookName;

        @Builder
        public BookSearchDto(String bookName){
            this.bookName = bookName;
        }


    }
}

package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class BookCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "book_catesgory_id")
    private long bookCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;


}

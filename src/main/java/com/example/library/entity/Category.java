package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    List<BookCategory> bookCategories= new ArrayList<>();
}

package com.agh.bookstoreProducts.Book;

import lombok.Data;

@Data
public class CategorySummary {
    private Category category;
    private int count;

    public CategorySummary(Category category, int count) {
        this.category = category;
        this.count = count;
    }
}

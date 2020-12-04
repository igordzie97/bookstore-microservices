package com.agh.bookstoreProducts.Book;

import com.agh.bookstoreProducts.Authors.Author;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int length;

    private int stock;

    private String description;

    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String photoName;

    public Book(){

    }

    public void incrementStockCount() {
        this.stock++;
    }

    public void decrementStockCount() {
        this.stock--;
    }
}

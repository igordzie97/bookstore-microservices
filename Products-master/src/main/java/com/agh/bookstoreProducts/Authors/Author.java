package com.agh.bookstoreProducts.Authors;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "authors")
public class Author {
    private @Id @GeneratedValue
    Long id;


    @NotBlank
    private String name;


    @Min(1)
    @NotNull()
    @Range(min = 1)
    private Integer year;

//    @OneToMany
//    private Country coutry;


    @NotBlank
    private String description;


    private String photoName;

    public Author() {

    }

    public Author(String name, Integer year, String description) {
        this.name = name;
        this.year = year;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

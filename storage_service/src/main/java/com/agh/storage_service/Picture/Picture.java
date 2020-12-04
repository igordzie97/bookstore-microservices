package com.agh.storage_service.Picture;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Picture {
    private @Id @GeneratedValue
    Long id;
    private String name;
    private String url;

    public Picture() {}

    public Picture(String name, String url) {
        this.name = name;
        this.url = url;
        //todo upload pliku i zapis....
    }
}

package com.agh.bookstoreProducts;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id){
        super("Resource "+id+" not found. Error 404.");
    }

    public NotFoundException(Long id, String resourceName){
        super(resourceName + " "+id+" not found. Error 404.");
    }
}

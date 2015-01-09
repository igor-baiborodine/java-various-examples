package com.kiroule.example.restwebapp.exceptions;

/**
 * @author Igor Baiborodine
 */
public class EntityExistsException extends RuntimeException {

    public EntityExistsException(String message) {
        super(message);
    }
}

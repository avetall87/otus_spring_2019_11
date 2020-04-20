package ru.spb.spring.libraryapp.events.exception;

public class DeleteAuthorConstraintException extends RuntimeException {

    public DeleteAuthorConstraintException(String message) {
        super(message);
    }

}

package ru.spb.spring.libraryapp.events.exception;

public class DeleteBookConstraintException extends RuntimeException {

    public DeleteBookConstraintException(String message) {
        super(message);
    }

}

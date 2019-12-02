package com.github.isenng.libraryintegrationtesting.service.library.exceptions;

public class BookDoesNotExistException extends LibraryException {
    public BookDoesNotExistException() {
    }

    public BookDoesNotExistException(String message) {
        super(message);
    }

    public BookDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public BookDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

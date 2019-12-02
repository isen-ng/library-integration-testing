package com.github.isenng.libraryintegrationtesting.service.library.exceptions;

public class AlreadyCheckedOutException extends LibraryException {
    public AlreadyCheckedOutException() {
    }

    public AlreadyCheckedOutException(String message) {
        super(message);
    }

    public AlreadyCheckedOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyCheckedOutException(Throwable cause) {
        super(cause);
    }

    public AlreadyCheckedOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

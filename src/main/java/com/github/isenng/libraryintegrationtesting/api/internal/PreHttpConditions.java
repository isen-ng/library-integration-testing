package com.github.isenng.libraryintegrationtesting.api.internal;

import com.github.isenng.libraryintegrationtesting.internal.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PreHttpConditions {
    public static void checkNotNullOrEmpty(String input, String inputName) {
        if (StringUtils.IsNullOrEmpty(input)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, inputName + " is required");
        }
    }
}

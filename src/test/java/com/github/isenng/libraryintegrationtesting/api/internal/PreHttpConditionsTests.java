package com.github.isenng.libraryintegrationtesting.api.internal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PreHttpConditionsTests {
    @Nested
    class CheckNotNullOrEmpty {
        @Test
        void shouldThrowResponseStatusExceptionWith400IfInputIsNull() {
            // act
            Throwable t = catchThrowable(() -> PreHttpConditions.checkNotNullOrEmpty(null, "name"));

            // assert
            assertThat(t).isInstanceOf(ResponseStatusException.class);

            ResponseStatusException responseStatusException = (ResponseStatusException) t;
            assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void shouldThrowResponseStatusExceptionWith400IfInputIsEmpty() {
            // act
            Throwable t = catchThrowable(() -> PreHttpConditions.checkNotNullOrEmpty("", "name"));

            // assert
            assertThat(t).isInstanceOf(ResponseStatusException.class);

            ResponseStatusException responseStatusException = (ResponseStatusException) t;
            assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void shouldNotThrowExceptionIfInputIsNotEmpty() {
            // act
            Throwable t = catchThrowable(() -> PreHttpConditions.checkNotNullOrEmpty("abcd", "name"));

            // assert
            assertThat(t).isNull();
        }
    }
}
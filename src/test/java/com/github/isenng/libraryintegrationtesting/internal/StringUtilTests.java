package com.github.isenng.libraryintegrationtesting.internal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilTests {
    @Nested
    class IsNullOrEmpty {
        @Test
        void shouldReturnTrueIfInputIsNull() {
            // act
            boolean result = StringUtils.IsNullOrEmpty(null);

            // assert
            assertThat(result).isTrue();
        }

        @Test
        void shouldReturnTrueIfInputIsEmpty() {
            // act
            boolean result = StringUtils.IsNullOrEmpty("");

            // assert
            assertThat(result).isTrue();
        }

        @Test
        void shouldReturnFalseIfInputIsNotEmpty() {
            // act
            boolean result = StringUtils.IsNullOrEmpty("abcd");

            // assert
            assertThat(result).isFalse();
        }
    }
}

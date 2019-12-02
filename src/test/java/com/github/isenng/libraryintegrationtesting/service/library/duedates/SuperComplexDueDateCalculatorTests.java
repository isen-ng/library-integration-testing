package com.github.isenng.libraryintegrationtesting.service.library.duedates;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

public class SuperComplexDueDateCalculatorTests {
    private SuperComplexDueDateCalculator calculator;
    private Clock mockClock;

    public SuperComplexDueDateCalculatorTests() {
        mockClock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
        calculator = new SuperComplexDueDateCalculator();

        calculator.timeTravel(mockClock);
    }

    @Nested
    class CalculateDueDate {
        @Test
        void shouldReturnDueDate2WeeksFromNow() {
            // arrange
            long expected = LocalDate
                    .now(mockClock)
                    .plusDays(14)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toEpochSecond();

            // act
            long result = calculator.calculateDueDate();

            // assert
            assertThat(result).isEqualTo(expected);
        }
    }
}

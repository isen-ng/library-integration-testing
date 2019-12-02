package com.github.isenng.libraryintegrationtesting.service.library.duedates;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class SuperComplexDueDateCalculator implements DueDateCalculator {
    private Clock clock;

    public SuperComplexDueDateCalculator() {
        clock = Clock.systemUTC();
    }

    @Override
    public long calculateDueDate() {
        return LocalDate
                .now(clock)
                .plusDays(14)
                .atStartOfDay(ZoneOffset.UTC)
                .toEpochSecond();
    }

    void timeTravel(Clock clock) {
        this.clock = clock;
    }
}

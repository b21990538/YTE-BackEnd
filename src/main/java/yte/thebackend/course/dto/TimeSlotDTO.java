package yte.thebackend.course.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public record TimeSlotDTO(
        @Min(value = 1, message = "day value cannot be less than 1")
        @Max(value = 5, message = "day value cannot be more than 5")
        Integer day,
        @Min(value = 1, message = "slot value cannot be less than 1")
        @Max(value = 8, message = "slot value cannot be more than 8")
        Integer slot
) {
}

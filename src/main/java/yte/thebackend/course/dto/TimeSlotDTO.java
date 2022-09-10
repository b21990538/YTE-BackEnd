package yte.thebackend.course.dto;

import yte.thebackend.course.entity.TimeSlot;

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
        public TimeSlot toEntity() {
                return new TimeSlot(day, slot);
        }

        public static TimeSlotDTO fromEntity(TimeSlot timeSlot) {
                return new TimeSlotDTO(timeSlot.getDay(), timeSlot.getSlot());
        }
}

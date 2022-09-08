package yte.thebackend.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.course.entity.TimeSlot;
import yte.thebackend.course.repository.TimeSlotRepository;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @PostConstruct
    public void init() {
        for (int i = 10; i < 60; i+=10) {
            for (int j = 1; j < 9; j++) {
                // setup timeslots from 11 to 58
                timeSlotRepository.save(new TimeSlot(i+j));
            }
        }
    }

}

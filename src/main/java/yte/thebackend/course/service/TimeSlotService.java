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

    @PostConstruct  //TODO remove
    public void init() {
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 9; j++) {
                timeSlotRepository.save(new TimeSlot(i, j));
            }
        }
    }

}

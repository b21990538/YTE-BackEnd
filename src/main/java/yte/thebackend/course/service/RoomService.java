package yte.thebackend.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.repository.RoomRepository;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @PostConstruct
    public void init() {
        for (int i = 1; i < 11; i++) {
            roomRepository.save(new Room("d" + i));
        }
    }
}

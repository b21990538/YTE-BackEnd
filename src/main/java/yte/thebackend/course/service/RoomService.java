package yte.thebackend.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.repository.RoomRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @PostConstruct  //TODO remove
    public void init() {
        for (int i = 1; i < 11; i++) {
            roomRepository.save(new Room("d" + i));
        }
    }

    public List<Room> getRoomsByNameLike(String like) {
        return roomRepository.findByNameContainingIgnoreCase(like, PageRequest.of(0, 10));
    }
}

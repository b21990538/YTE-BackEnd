package yte.thebackend.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.repository.RoomRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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

    public Room getRoomByName(String name) {
        Optional<Room> OptRoom = roomRepository.findByName(name);

        if (OptRoom.isEmpty()) {
            throw new EntityNotFoundException("Room with name %s not found".formatted(name));
        }
        return OptRoom.get();
    }

    public List<Room> getRoomsByNameLike(String like) {
        return roomRepository.findByNameContainingIgnoreCase(like, PageRequest.of(0, 10));
    }

    public Room getRoomFromCourse(Course course) {
        Optional<Room> OptRoom = roomRepository.findByName(course.getRoom().getName());

        if (OptRoom.isEmpty()) {
            throw new EntityNotFoundException("Room with name %s not found".formatted(course.getRoom().getName()));
        }
        return OptRoom.get();
    }
}

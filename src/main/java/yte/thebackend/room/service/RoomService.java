package yte.thebackend.room.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.jar.asm.commons.Remapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.course.entity.Course;
import yte.thebackend.room.entity.Room;
import yte.thebackend.room.repository.RoomRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    public Stream<Room> getRooms() {
        return roomRepository.findAll().stream();
    }

    @Transactional
    public MessageResponse addRoom(Room room) {
        roomRepository.save(room);
        return new MessageResponse("Room added", ResultType.SUCCESS);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with ID %d not found".formatted(id)));
    }

    public MessageResponse updateRoom(Long id, Room newRoom) {
        Room room = getRoomById(id);
        room.update(newRoom);

        roomRepository.save(room);
        return new MessageResponse("Room with id %d has been updated".formatted(id), ResultType.SUCCESS);
    }

    public MessageResponse deleteRoom(Long id) {
        roomRepository.deleteById(id);
        // TODO handle cascade
        return new MessageResponse("Room with id %d deleted".formatted(id), ResultType.SUCCESS);
    }
}

package yte.thebackend.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.enums.AccountTypes;
import yte.thebackend.common.repository.UserRepository;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.course.dto.CourseAddRequest;
import yte.thebackend.course.dto.TimeSlotDTO;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.entity.TimeSlot;
import yte.thebackend.course.repository.CourseRepository;
import yte.thebackend.course.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    public MessageResponse addCourse(CourseAddRequest courseAddRequest) {
        Optional<User> OptLecturer = userRepository.findByUsername(courseAddRequest.lectUsername());
        if (OptLecturer.isEmpty()) {
            return new MessageResponse("Lecturer with username %s not found".formatted(courseAddRequest.lectUsername()),
                    ResultType.ERROR);
        }
        User lecturer = OptLecturer.get();
        String role = lecturer.getAuthorities().get(0).getAuthority();

        if (!role.equals("LECTURER")) {
            return new MessageResponse("User %s is not a lecturer".formatted(courseAddRequest.lectUsername()),
                    ResultType.ERROR);
        }

        Room room = new Room(courseAddRequest.room());
        room = roomRepository.save(room);
        List<TimeSlot> timeSlots = new ArrayList<>();

        for (TimeSlotDTO timeSlotDTO :
                courseAddRequest.timeSlots()) {
            timeSlots.add(new TimeSlot(timeSlotDTO.day()*10 + timeSlotDTO.slot()));
        }

        courseRepository.save(new Course(courseAddRequest.name(), courseAddRequest.description(),
                courseAddRequest.type(), courseAddRequest.code(),
                timeSlots, room, lecturer));

        return new MessageResponse("Success", ResultType.SUCCESS);
    }
}

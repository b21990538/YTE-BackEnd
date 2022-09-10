package yte.thebackend.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.repository.UserRepository;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.repository.CourseRepository;
import yte.thebackend.course.repository.RoomRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    public MessageResponse addCourse(Course course) {
        String lecturerUsername = course.getLecturer().getUsername();
        Optional<User> OptLecturer = userRepository.findByUsername(lecturerUsername);

        if (OptLecturer.isEmpty()) {
            return new MessageResponse("Lecturer with username %s not found".formatted(lecturerUsername),
                    ResultType.ERROR);
        }

        User lecturer = OptLecturer.get();
        String role = lecturer.getAuthorities().get(0).getAuthority();

        if (!role.equals("LECTURER")) {
            return new MessageResponse("User %s is not a lecturer".formatted(lecturerUsername),
                    ResultType.ERROR);
        }

        Optional<Room> OptRoom = roomRepository.findByName(course.getRoom().getName());

        if (OptRoom.isEmpty()) {
            return new MessageResponse("Room with name %s not found".formatted(course.getRoom().getName()),
                    ResultType.ERROR);
        }

        course.setRoom(OptRoom.get());
        course.setLecturer(lecturer);

        courseRepository.save(course);

        return new MessageResponse("Course added", ResultType.SUCCESS);
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public MessageResponse deleteCourse(Long id) {
        courseRepository.deleteById(id);

        return new MessageResponse("Course with id %d deleted".formatted(id), ResultType.SUCCESS);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID %d not found".formatted(id)));
    }

    public MessageResponse updateCourse(Long id, Course newCourse) {
        Course oldCourse = getCourseById(id);
        oldCourse.update(newCourse);

        String lecturerUsername = oldCourse.getLecturer().getUsername();
        Optional<User> OptLecturer = userRepository.findByUsername(lecturerUsername);

        if (OptLecturer.isEmpty()) {
            return new MessageResponse("Lecturer with username %s not found".formatted(lecturerUsername),
                    ResultType.ERROR);
        }

        User lecturer = OptLecturer.get();
        String role = lecturer.getAuthorities().get(0).getAuthority();

        if (!role.equals("LECTURER")) {
            return new MessageResponse("User %s is not a lecturer".formatted(lecturerUsername),
                    ResultType.ERROR);
        }

        Optional<Room> OptRoom = roomRepository.findByName(oldCourse.getRoom().getName());

        if (OptRoom.isEmpty()) {
            return new MessageResponse("Room with name %s not found".formatted(oldCourse.getRoom().getName()),
                    ResultType.ERROR);
        }

        Room room = OptRoom.get();

        oldCourse.setRoom(room);
        oldCourse.setLecturer(lecturer);

        courseRepository.save(oldCourse);
        return new MessageResponse("Course with id %d has been updated".formatted(id), ResultType.SUCCESS);
    }
}

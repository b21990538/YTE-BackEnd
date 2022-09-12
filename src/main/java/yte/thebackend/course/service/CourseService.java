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
        validateAndAddCourse(course);

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

        validateAndAddCourse(oldCourse);
        return new MessageResponse("Course with id %d has been updated".formatted(id), ResultType.SUCCESS);
    }

    public void validateAndAddCourse(Course course) {
        String lecturerUsername = course.getLecturer().getUsername();
        Optional<User> OptLecturer = userRepository.findByUsername(lecturerUsername);

        checkUserWithUsernameExists(lecturerUsername, OptLecturer);
        User lecturer = OptLecturer.get();
        String role = lecturer.getAuthorities().get(0).getAuthority();

        checkUserIsLecturer(lecturerUsername, role);

        Room room = getRoomFromCourse(course);

        course.setRoom(room);
        course.setLecturer(lecturer);

        courseRepository.save(course);
    }

    public Room getRoomFromCourse(Course course) {
        Optional<Room> OptRoom = roomRepository.findByName(course.getRoom().getName());

        if (OptRoom.isEmpty()) {
            throw new EntityNotFoundException("Room with name %s not found".formatted(course.getRoom().getName()));
        }
        return OptRoom.get();
    }

    public static void checkUserIsLecturer(String lecturerUsername, String role) {
        if (!role.equals("LECTURER")) {
            throw new RuntimeException("User %s is not a lecturer".formatted(lecturerUsername));
        }
    }

    public static void checkUserWithUsernameExists(String username, Optional<User> OptUser) {
        if (OptUser.isEmpty()) {
            throw new EntityNotFoundException("Lecturer with username %s not found".formatted(username));
        }
    }
}

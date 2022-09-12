package yte.thebackend.course.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.enums.AccountTypes;
import yte.thebackend.common.repository.UserRepository;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.repository.CourseRepository;
import yte.thebackend.course.repository.RoomRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyCoursesService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    private final CourseService courseService;

    public List<Course> getMyCourses(User user) {
        String role = user.getFirstAuthority();

        if (role.equals(AccountTypes.LECTURER.name())) {
            return getLecturerCourses(user.getId());
        }
        if (role.equals(AccountTypes.ASSISTANT.name())) {
            return getAssistantCourses(user);
        }

        return getStudentCourses(user.getId());
    }

    private List<Course> getStudentCourses(Long studentUserId) {
        //TODO get student courses
        return new ArrayList<>();
    }

    private List<Course> getAssistantCourses(User assistant) {
        return courseRepository.findByAssistantsIsContaining(assistant);
    }

    private List<Course> getLecturerCourses(Long lecturerId) {
        return courseRepository.findByLecturer_Id(lecturerId);
    }
    //TODO remove assistant? Transactional?
    public MessageResponse addAssistant(User lecturer, Long courseId, String assistantUsername) {
        Optional<User> OptAssistant = userRepository.findByUsername(assistantUsername);
        CourseService.checkUserWithUsernameExists(assistantUsername, OptAssistant);

        Course course = courseService.getCourseById(courseId);
        User assistant = OptAssistant.get();

        checkUserIsLecturerOfCourse(lecturer, courseId, course);
        checkUserIsAssistant(assistantUsername, assistant);

        course.addAssistant(assistant);

        courseRepository.save(course);

        return new MessageResponse("Assistant added", ResultType.SUCCESS);
    }

    public MessageResponse updateCourse(User user, Long courseId, Course newCourse) {
        Course oldCourse = courseService.getCourseById(courseId);
        oldCourse.editLimitedUpdate(newCourse);

        checkUserIsGivingCourse(user, courseId, oldCourse);

        Room room = courseService.getRoomFromCourse(oldCourse);
        oldCourse.setRoom(room);

        courseRepository.save(oldCourse);
        return new MessageResponse("Course with id %d has been updated".formatted(courseId), ResultType.SUCCESS);
    }

    private static void checkUserIsAssistant(String assistantUsername, User assistant) {
        if (!assistant.getFirstAuthority().equals(AccountTypes.ASSISTANT.name())) {
            throw new RuntimeException("User %s is not an assistant".formatted(assistantUsername));
        }
    }

    private static void checkUserIsGivingCourse(User user, Long courseId, Course course) {
        if (user.getFirstAuthority().equals(AccountTypes.LECTURER.name())) {
            checkUserIsLecturerOfCourse(user, courseId, course);
        } else {
            if (!course.getAssistants().contains(user)) {
                throw new RuntimeException("You are not an assistant of the course with id %d".formatted(courseId));
            }
        }
    }

    private static void checkUserIsLecturerOfCourse(User user, Long courseId, Course course) {
        if (!course.getLecturer().equals(user)) {
            throw new RuntimeException("You are not the lecturer of the course with id %d".formatted(courseId));
        }
    }

    private Optional<Room> getOptionalRoom(Course course) {
        Optional<Room> OptRoom = roomRepository.findByName(course.getRoom().getName());

        if (OptRoom.isEmpty()) {
            throw new RuntimeException("Room with name %s not found".formatted(course.getRoom().getName()));
        }
        return OptRoom;
    }
}

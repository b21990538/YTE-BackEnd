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
        //TODO
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
        Optional<Course> OptCourse = courseRepository.findById(courseId);
        Optional<User> OptAssistant = userRepository.findByUsername(assistantUsername);

        if (OptCourse.isEmpty()) {
            return new MessageResponse("Course with id %d not found".formatted(courseId), ResultType.ERROR);
        }
        if (OptAssistant.isEmpty()) {
            return new MessageResponse("Assistant %s not found".formatted(assistantUsername), ResultType.ERROR);
        }

        Course course = OptCourse.get();
        User assistant = OptAssistant.get();

        if (!course.getLecturer().equals(lecturer)) {
            return new MessageResponse("You are not the lecturer of the course with id %d".formatted(courseId),
                    ResultType.ERROR);
        }

        if (!assistant.getFirstAuthority().equals(AccountTypes.ASSISTANT.name())) {
            return new MessageResponse("User %s is not an assistant".formatted(assistantUsername), ResultType.ERROR);
        }

        course.addAssistant(assistant);

        courseRepository.save(course);

        return new MessageResponse("Assistant added", ResultType.SUCCESS);
    }

    public MessageResponse updateCourse(User user, Long courseId, Course newCourse) {
        Course oldCourse = courseService.getCourseById(courseId);
        oldCourse.editLimitedUpdate(newCourse);

        Optional<Room> OptRoom = roomRepository.findByName(oldCourse.getRoom().getName());

        if (OptRoom.isEmpty()) {
            return new MessageResponse("Room with name %s not found".formatted(oldCourse.getRoom().getName()),
                    ResultType.ERROR);
        }

        if (user.getFirstAuthority().equals(AccountTypes.LECTURER.name())) {
            if (!oldCourse.getLecturer().equals(user)) {
                return new MessageResponse("You are not the lecturer of the course with id %d".formatted(courseId),
                        ResultType.ERROR);
            }
        } else {    //TODO check user giving course
            if (!oldCourse.getAssistants().contains(user)) {
                return new MessageResponse("You are not an assistant of the course with id %d".formatted(courseId),
                        ResultType.ERROR);
            }
        }

        Room room = OptRoom.get();
        oldCourse.setRoom(room);

        courseRepository.save(oldCourse);
        return new MessageResponse("Course with id %d has been updated".formatted(courseId), ResultType.SUCCESS);
    }
}

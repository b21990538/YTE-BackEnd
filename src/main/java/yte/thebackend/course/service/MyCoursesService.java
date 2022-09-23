package yte.thebackend.course.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.repository.UserRepository;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.common.service.AssistantService;
import yte.thebackend.course.entity.Course;
import yte.thebackend.room.entity.Room;
import yte.thebackend.course.repository.CourseRepository;
import yte.thebackend.room.service.RoomService;
import yte.thebackend.student.entity.TakingCourse;
import yte.thebackend.student.repository.TakingCourseRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyCoursesService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseService courseService;
    private final AssistantService assistantService;
    private final RoomService roomService;
    private final TakingCourseRepository takingCourseRepository;

    public List<Course> getStudentCourses(Long studentUserId) {

        return takingCourseRepository.findByStudent_Id(studentUserId).stream()
                .map(TakingCourse::getCourse)
                .toList();
    }

    public List<Course> getAssistantCourses(Assistant assistant) {
        return courseRepository.findByAssistantsIsContaining(assistant);
    }

    public List<Course> getLecturerCourses(Long lecturerId) {
        return courseRepository.findByLecturer_Id(lecturerId);
    }
    // TODO remove assistant?
    @Transactional
    public MessageResponse assignAssistant(Lecturer lecturer, Long courseId, String assistantUsername) {

        Course course = courseService.getCourseById(courseId);
        Assistant assistant = assistantService.getAssistantByUsername(assistantUsername);

        checkLecturerGivesCourse(lecturer, course);

        course.addAssistant(assistant);

        courseRepository.save(course);

        return new MessageResponse("Assistant added", ResultType.SUCCESS);
    }

    public MessageResponse lecturerUpdateCourse(Lecturer lecturer, Long courseId, Course newCourse) {
        Course course = courseService.getCourseById(courseId);
        course.restrictedUpdate(newCourse);

        checkLecturerGivesCourse(lecturer, course);

        Room room = roomService.getRoomFromCourse(course);
        course.setRoom(room);

        courseRepository.save(course);
        return new MessageResponse("Course with id %d has been updated".formatted(courseId), ResultType.SUCCESS);
    }

    public MessageResponse assistantUpdateCourse(Assistant assistant, Long courseId, Course newCourse) {
        Course course = courseService.getCourseById(courseId);
        course.restrictedUpdate(newCourse);

        checkAssistantAssignedToCourse(assistant, course);

        Room room = roomService.getRoomFromCourse(course);
        course.setRoom(room);

        courseRepository.save(course);
        return new MessageResponse("Course with id %d has been updated".formatted(courseId), ResultType.SUCCESS);
    }

    public static void checkAssistantAssignedToCourse(Assistant assistant, Course course) {
        if (!course.getAssistants().contains(assistant)) {
            throw new RuntimeException("You are not an assistant of the course with id %d".formatted(course.getId()));
        }
    }

    public static void checkLecturerGivesCourse(Lecturer lecturer, Course course) {
        if (!course.getLecturer().equals(lecturer)) {
            throw new RuntimeException("You are not the lecturer of the course with id %d".formatted(course.getId()));
        }
    }
}

package yte.thebackend.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.common.service.LecturerService;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.repository.CourseRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final LecturerService lecturerService;
    private final RoomService roomService;

    public MessageResponse addCourse(Course course) {
        validateAndAddCourse(course);

        return new MessageResponse("Course added", ResultType.SUCCESS);
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public MessageResponse deleteCourse(Long id) {
        courseRepository.deleteById(id);
        //TODO handle cascade
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
        Lecturer lecturer = lecturerService.getLecturerByUsername(course.getLecturer().getUsername());
        Room room = roomService.getRoomFromCourse(course);

        course.setRoom(room);
        course.setLecturer(lecturer);

        courseRepository.save(course);
    }
}

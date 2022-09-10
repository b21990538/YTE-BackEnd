package yte.thebackend.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.repository.UserRepository;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.repository.CourseRepository;
import yte.thebackend.course.repository.RoomRepository;
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

        course.setRoom(roomRepository.save(course.getRoom()));
        course.setLecturer(lecturer);

        courseRepository.save(course);

        return new MessageResponse("Course added", ResultType.SUCCESS);
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public MessageResponse deleteCourse(Long id) {
        courseRepository.deleteById(id);

        return new MessageResponse("Course deleted", ResultType.SUCCESS);
    }
}

package yte.thebackend.exam_hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.repository.RoomRepository;
import yte.thebackend.course.service.CourseService;
import yte.thebackend.course.service.MyCoursesService;
import yte.thebackend.exam_hw.entity.Exam;
import yte.thebackend.exam_hw.repository.ExamRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final RoomRepository roomRepository;

    private final CourseService courseService;

    public MessageResponse addExam(Exam exam, User user) {
        Room room = getRoomByName(exam.getRoom().getName());
        exam.setRoom(room);

        Course course = courseService.getCourseById(exam.getCourse().getId());
        exam.setCourse(course);

        MyCoursesService.checkUserIsGivingCourse(user, course.getId(), course);

        examRepository.save(exam);

        return new MessageResponse("Exam added successfully.", ResultType.SUCCESS);
    }

    public Room getRoomByName(String name) {
        Optional<Room> OptRoom = roomRepository.findByName(name);

        if (OptRoom.isEmpty()) {
            throw new EntityNotFoundException("Room with name %s not found".formatted(name));
        }
        return OptRoom.get();
    }
}

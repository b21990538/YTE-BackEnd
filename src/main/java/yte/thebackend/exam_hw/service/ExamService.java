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
import yte.thebackend.student.entity.TakingCourse;
import yte.thebackend.student.entity.TakingExam;
import yte.thebackend.student.repository.TakingCourseRepository;
import yte.thebackend.student.repository.TakingExamRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final RoomRepository roomRepository;
    private final TakingCourseRepository takingCourseRepository;
    private final TakingExamRepository takingExamRepository;
    private final CourseService courseService;

    @Transactional
    public MessageResponse addExam(Exam exam, User user) {
        Room room = getRoomByName(exam.getRoom().getName());
        exam.setRoom(room);

        Course course = courseService.getCourseById(exam.getCourse().getId());
        exam.setCourse(course);

        MyCoursesService.checkUserIsGivingCourse(user, course.getId(), course);

        Exam savedExam = examRepository.save(exam);

        // Assign exam to students
        List<TakingExam> takingExams = new ArrayList<>();
        List<TakingCourse> takingCourses = takingCourseRepository.findByCourse_Id(course.getId());
        for (TakingCourse takingCourse :takingCourses) {
            takingExams.add(new TakingExam(takingCourse.getStudentId(), savedExam.getId()));
        }
        takingExamRepository.saveAll(takingExams);

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

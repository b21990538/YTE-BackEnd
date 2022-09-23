package yte.thebackend.exam_hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.course.entity.Course;
import yte.thebackend.room.entity.Room;
import yte.thebackend.course.service.CourseService;
import yte.thebackend.course.service.MyCoursesService;
import yte.thebackend.room.service.RoomService;
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

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final RoomService roomService;
    private final TakingCourseRepository takingCourseRepository;
    private final TakingExamRepository takingExamRepository;
    private final CourseService courseService;

    @Transactional
    public MessageResponse addExam(Exam exam, Lecturer lecturer) {
        Course course = courseService.getCourseById(exam.getCourse().getId());
        exam.setCourse(course);

        MyCoursesService.checkLecturerGivesCourse(lecturer, course);

        prepAndAddExam(exam, course);

        return new MessageResponse("Exam added successfully.", ResultType.SUCCESS);
    }

    @Transactional
    public MessageResponse addExam(Exam exam, Assistant assistant) {
        Course course = courseService.getCourseById(exam.getCourse().getId());
        exam.setCourse(course);

        MyCoursesService.checkAssistantAssignedToCourse(assistant, course);

        prepAndAddExam(exam, course);

        return new MessageResponse("Exam added successfully.", ResultType.SUCCESS);
    }

    private void prepAndAddExam(Exam exam, Course course) {
        Room room = roomService.getRoomByName(exam.getRoom().getName());
        exam.setRoom(room);

        Exam savedExam = examRepository.save(exam);

        // Assign exam to students
        List<TakingExam> takingExams = new ArrayList<>();
        List<TakingCourse> takingCourses = takingCourseRepository.findByCourse_Id(course.getId());
        for (TakingCourse takingCourse :takingCourses) {
            takingExams.add(new TakingExam(takingCourse.getStudentId(), savedExam.getId()));
        }
        takingExamRepository.saveAll(takingExams);
    }

    public List<Exam> getExams(Long courseId) {
        //TODO check user is affiliated with course

        return examRepository.findByCourse_Id(courseId);
    }

    public Exam getExamById(Long examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam with ID %d not found".formatted(examId)));
    }
}

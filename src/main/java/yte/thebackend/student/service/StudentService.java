package yte.thebackend.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.FileEntity;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.common.service.FileService;
import yte.thebackend.course.service.MyCoursesService;
import yte.thebackend.exam_hw.entity.Exam;
import yte.thebackend.exam_hw.entity.Homework;
import yte.thebackend.exam_hw.repository.ExamRepository;
import yte.thebackend.exam_hw.repository.HomeworkRepository;
import yte.thebackend.exam_hw.service.ExamService;
import yte.thebackend.exam_hw.service.HomeworkService;
import yte.thebackend.student.entity.Student;
import yte.thebackend.student.entity.TakingCourse;
import yte.thebackend.student.entity.TakingExam;
import yte.thebackend.student.entity.TakingHomework;
import yte.thebackend.student.repository.TakingCourseRepository;
import yte.thebackend.student.repository.TakingExamRepository;
import yte.thebackend.student.repository.TakingHomeworkRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final TakingCourseRepository takingCourseRepository;
    private final TakingExamRepository takingExamRepository;
    private final TakingHomeworkRepository takingHomeworkRepository;
    private final ExamRepository examRepository;
    private final HomeworkRepository homeworkRepository;
    private final FileService fileService;
    private final ExamService examService;
    private final HomeworkService homeworkService;

    @Transactional
    public MessageResponse takeCourse(Student student, Long courseId) {
        TakingCourse takingCourse = new TakingCourse(student.getId(), courseId);

        takingCourseRepository.save(takingCourse);

        // assign course exams and homeworks to student
        List<TakingExam> takingExams = new ArrayList<>();
        List<TakingHomework> takingHomeworks = new ArrayList<>();
        List<Exam> exams = examRepository.findByCourse_Id(courseId);
        List<Homework> homeworks = homeworkRepository.findByCourse_Id(courseId);
        for (Exam exam :exams) {
            takingExams.add(new TakingExam(student.getId(), exam.getId()));
        }
        for (Homework homework :homeworks) {
            takingHomeworks.add(new TakingHomework(student.getId(), homework.getId()));
        }
        takingExamRepository.saveAll(takingExams);
        takingHomeworkRepository.saveAll(takingHomeworks);

        return new MessageResponse("Successfully joined course.", ResultType.SUCCESS);
    }

    public List<TakingExam> getExamGrades(Lecturer lecturer, Long examId) {
        Exam exam = examService.getExamById(examId);
        MyCoursesService.checkLecturerGivesCourse(lecturer, exam.getCourse());

        return takingExamRepository.findByExam_Id(examId);
    }

    public List<TakingExam> getExamGrades(Assistant assistant, Long examId) {
        Exam exam = examService.getExamById(examId);
        MyCoursesService.checkAssistantAssignedToCourse(assistant, exam.getCourse());

        return takingExamRepository.findByExam_Id(examId);
    }

    @Transactional
    public List<TakingHomework> getHomeworkGrades(Lecturer lecturer, Long homeworkId) {
        Homework homework = homeworkService.getHomeworkById(homeworkId);
        MyCoursesService.checkLecturerGivesCourse(lecturer, homework.getCourse());

        return takingHomeworkRepository.findByHomework_Id(homeworkId);
    }

    @Transactional
    public List<TakingHomework> getHomeworkGrades(Assistant assistant, Long homeworkId) {
        Homework homework = homeworkService.getHomeworkById(homeworkId);
        MyCoursesService.checkAssistantAssignedToCourse(assistant, homework.getCourse());

        return takingHomeworkRepository.findByHomework_Id(homeworkId);
    }

    @Transactional
    public MessageResponse updateExamGrade(Lecturer lecturer, Long examId, Long studentId, Integer score) {
        Exam exam = examService.getExamById(examId);
        MyCoursesService.checkLecturerGivesCourse(lecturer, exam.getCourse());

        TakingExam takingExam = getTakingExamByIds(studentId, examId);
        takingExam.setScore(score);
        takingExamRepository.save(takingExam);

        return new MessageResponse("Score set.", ResultType.SUCCESS);
    }

    @Transactional
    public MessageResponse updateExamGrade(Assistant assistant, Long examId, Long studentId, Integer score) {
        Exam exam = examService.getExamById(examId);
        MyCoursesService.checkAssistantAssignedToCourse(assistant, exam.getCourse());

        TakingExam takingExam = getTakingExamByIds(studentId, examId);
        takingExam.setScore(score);
        takingExamRepository.save(takingExam);

        return new MessageResponse("Score set.", ResultType.SUCCESS);
    }

    @Transactional
    public MessageResponse updateHomeworkGrade(Lecturer lecturer, Long homeworkId, Long studentId, Integer score) {
        Homework homework = homeworkService.getHomeworkById(homeworkId);
        MyCoursesService.checkLecturerGivesCourse(lecturer, homework.getCourse());

        TakingHomework takingHomework = getTakingHomeworkByIds(studentId, homeworkId);
        takingHomework.setScore(score);
        takingHomeworkRepository.save(takingHomework);

        return new MessageResponse("Score set.", ResultType.SUCCESS);
    }

    @Transactional
    public MessageResponse updateHomeworkGrade(Assistant assistant, Long homeworkId, Long studentId, Integer score) {
        Homework homework = homeworkService.getHomeworkById(homeworkId);
        MyCoursesService.checkAssistantAssignedToCourse(assistant, homework.getCourse());

        TakingHomework takingHomework = getTakingHomeworkByIds(studentId, homeworkId);
        takingHomework.setScore(score);
        takingHomeworkRepository.save(takingHomework);

        return new MessageResponse("Score set.", ResultType.SUCCESS);
    }

    public TakingExam getTakingExamByIds(Long studentId, Long examId) {
        return takingExamRepository.findByStudent_IdAndExam_Id(studentId, examId)
                .orElseThrow(() -> new EntityNotFoundException("Taking Exam not found"));
    }

    public TakingHomework getTakingHomeworkByIds(Long studentId, Long homeworkId) {
        return takingHomeworkRepository.findByStudent_IdAndHomework_Id(studentId, homeworkId)
                .orElseThrow(() -> new EntityNotFoundException("Taking Exam not found"));
    }

    @Transactional
    public MessageResponse addFileToHomework(MultipartFile file, Student student, Long homeworkId) {
        TakingHomework takingHomework = getTakingHomeworkByIds(student.getId(), homeworkId);
        FileEntity fileEntity = fileService.saveFile(file);
        takingHomework.setFile(fileEntity);

        takingHomeworkRepository.save(takingHomework);

        return new MessageResponse("File added.", ResultType.SUCCESS);
    }

    @Transactional
    public FileEntity getTakingHomeworkFile(Long studentId, Long homeworkId) {
        TakingHomework takingHomework = getTakingHomeworkByIds(studentId, homeworkId);
        return takingHomework.getFile();
    }

    @Transactional
    public Integer getHomeworkScoreByIds(Long studentId, Long homeworkId) {
        return getTakingHomeworkByIds(studentId,homeworkId).getScore();
    }

    @Transactional
    public Integer getExamScoreByIds(Long studentId, Long examId) {
        return getTakingExamByIds(studentId, examId).getScore();
    }

    public List<Integer> getExamGradeData(Long examId) {
        return takingExamRepository.findByExam_Id(examId).stream()
                .map(TakingExam::getScore)
                .toList();
    }

    @Transactional
    public List<Integer> getHomeworkGradeData(Long homeworkId) {
        return takingHomeworkRepository.findByHomework_Id(homeworkId).stream()
                .map(TakingHomework::getScore)
                .toList();
    }
}

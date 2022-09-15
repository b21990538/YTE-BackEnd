package yte.thebackend.exam_hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yte.thebackend.common.entity.FileEntity;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.repository.UserRepository;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.common.service.FileService;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.service.CourseService;
import yte.thebackend.course.service.MyCoursesService;
import yte.thebackend.exam_hw.entity.Homework;
import yte.thebackend.exam_hw.repository.HomeworkRepository;
import yte.thebackend.student.entity.TakingCourse;
import yte.thebackend.student.entity.TakingHomework;
import yte.thebackend.student.repository.TakingCourseRepository;
import yte.thebackend.student.repository.TakingHomeworkRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final UserRepository userRepository;
    private final TakingCourseRepository takingCourseRepository;
    private final TakingHomeworkRepository takingHomeworkRepository;
    private final CourseService courseService;
    private final FileService fileService;

    @Transactional
    public MessageResponse addHomework(Homework homework, MultipartFile multipartFile, User user) {

        //TODO findByUsername in UserService
        Optional<User> OptAssistant = userRepository.findByUsername(homework.getAssistant().getUsername());
        CourseService.checkUserWithUsernameExists(homework.getAssistant().getUsername(), OptAssistant);
        User assistant = OptAssistant.get();

        Course course = courseService.getCourseById(homework.getCourse().getId());

        MyCoursesService.checkUserIsAssistant(assistant.getUsername(), assistant);
        MyCoursesService.checkUserIsGivingCourse(assistant, course.getId(), course);// check assistant assigned to hw
        MyCoursesService.checkUserIsGivingCourse(user, course.getId(), course); // check person adding the homework

        FileEntity fileEntity = fileService.saveFile(multipartFile);

        homework.setAssistant(assistant);
        homework.setCourse(course);
        homework.setFile(fileEntity);

        Homework savedHomework = homeworkRepository.save(homework);

        // Assign homework to students
        List<TakingHomework> takingHomeworks = new ArrayList<>();
        List<TakingCourse> takingCourses = takingCourseRepository.findByCourse_Id(course.getId());
        for (TakingCourse takingCourse :takingCourses) {
            takingHomeworks.add(new TakingHomework(takingCourse.getStudentId(), savedHomework.getId()));
        }
        takingHomeworkRepository.saveAll(takingHomeworks);

        return new MessageResponse("Homework added successfully", ResultType.SUCCESS);
    }

    public FileEntity getHomeworkFile(Long homeworkId) {
        Homework homework = getHomeworkById(homeworkId);
        return homework.getFile();
    }

    public Homework getHomeworkById(Long homeworkId) {
        return homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new EntityNotFoundException("Homework with ID %d not found".formatted(homeworkId)));
    }

    @Transactional
    public List<Homework> getHomeworks(Long courseId) {
        //TODO check user is affiliated with course

        return homeworkRepository.findByCourse_Id(courseId);
    }
}

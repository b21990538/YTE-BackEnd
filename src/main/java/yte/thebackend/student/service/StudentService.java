package yte.thebackend.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.student.entity.TakingCourse;
import yte.thebackend.student.repository.TakingCourseRepository;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final TakingCourseRepository takingCourseRepository;

    public MessageResponse takeCourse(User student, Long courseId) {
        TakingCourse takingCourse = new TakingCourse(student.getId(), courseId);

        takingCourseRepository.save(takingCourse);
        //TODO assign exam and homeworks

        return new MessageResponse("Successfully joined course.", ResultType.SUCCESS);
    }
}

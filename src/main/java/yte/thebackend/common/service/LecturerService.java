package yte.thebackend.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.repository.LecturerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    public Lecturer getLecturerByUsername(String username) {
        Optional<Lecturer> OptLecturer = lecturerRepository.findByUsername(username);
        if (OptLecturer.isEmpty()) {
            throw new EntityNotFoundException("Lecturer with username %s not found".formatted(username));
        }
        return OptLecturer.get();
    }
}

package yte.thebackend.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.repository.AssistantRepository;
import yte.thebackend.common.repository.LecturerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutocompleteService {

    private final LecturerRepository lecturerRepository;
    private final AssistantRepository assistantRepository;

    public List<Lecturer> getLecturersByUsernameLike(String like) {
        return lecturerRepository.findByUsernameContainingIgnoreCase(like, PageRequest.of(0, 10));
    }

    public List<Assistant> getAssistantsByUsernameLike(String like) {
        return assistantRepository.findByUsernameContainingIgnoreCase(like, PageRequest.of(0, 10));
    }
}

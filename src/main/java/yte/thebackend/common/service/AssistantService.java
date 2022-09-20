package yte.thebackend.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.repository.AssistantRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssistantService {

    private final AssistantRepository assistantRepository;

    public Assistant getAssistantByUsername(String username) {
        Optional<Assistant> OptAssistant = assistantRepository.findByUsername(username);
        if (OptAssistant.isEmpty()) {
            throw new EntityNotFoundException("Assistant with username %s not found".formatted(username));
        }
        return OptAssistant.get();
    }
}

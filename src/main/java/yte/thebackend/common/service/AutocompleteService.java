package yte.thebackend.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.Authority;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.repository.AuthorityRepository;
import yte.thebackend.common.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutocompleteService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public List<User> getLecturersByUsernameLike(String like) {
        Authority authorityLECTURER = authorityRepository.findByAuthority("LECTURER").get();

        return userRepository.findByUsernameContainingIgnoreCaseAndAuthoritiesContaining(like, authorityLECTURER,
                PageRequest.of(0, 10));
    }

    public List<User> getAssistantsByUsernameLike(String like) {
        Authority authority = authorityRepository.findByAuthority("ASSISTANT").get();
        return userRepository.findByUsernameContainingIgnoreCaseAndAuthoritiesContaining(like, authority,
                PageRequest.of(0, 10));
    }
}

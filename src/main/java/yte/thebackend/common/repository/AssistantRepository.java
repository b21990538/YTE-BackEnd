package yte.thebackend.common.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.Lecturer;

import java.util.List;
import java.util.Optional;

public interface AssistantRepository extends JpaRepository<Assistant, Long> {

    List<Assistant> findByUsernameContainingIgnoreCase(String like, Pageable pageable);

    Optional<Assistant> findByUsername(String username);
}

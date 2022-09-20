package yte.thebackend.common.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.common.entity.Authority;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.entity.User;

import java.util.List;
import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    List<Lecturer> findByUsernameContainingIgnoreCase(String like, Pageable pageable);

    Optional<Lecturer> findByUsername(String username);
}

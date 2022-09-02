package yte.thebackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.entity.AuthUser;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByUsername(String username);
}

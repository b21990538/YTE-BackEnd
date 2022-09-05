package yte.thebackend.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.common.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}

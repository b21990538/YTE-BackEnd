package yte.thebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {


}

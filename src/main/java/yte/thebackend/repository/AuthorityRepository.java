package yte.thebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthority(String authority); // TODO can return Optional
}

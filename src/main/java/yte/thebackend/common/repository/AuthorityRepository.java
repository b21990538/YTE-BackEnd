package yte.thebackend.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.common.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthority(String authority);
}

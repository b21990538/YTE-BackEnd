package yte.thebackend.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.common.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthority(String authority); // TODO can return Optional
}

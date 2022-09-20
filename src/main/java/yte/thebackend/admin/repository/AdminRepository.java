package yte.thebackend.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}

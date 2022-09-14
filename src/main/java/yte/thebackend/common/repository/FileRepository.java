package yte.thebackend.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.common.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}

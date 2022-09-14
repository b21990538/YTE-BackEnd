package yte.thebackend.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import yte.thebackend.common.entity.FileEntity;
import yte.thebackend.common.repository.FileRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public FileEntity saveFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity fileEntity = null;
        try {
            fileEntity = new FileEntity(fileName, file.getContentType(), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileRepository.save(fileEntity);
    }

    public FileEntity getFileByID(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File with ID %d not found".formatted(id)));
    }
}

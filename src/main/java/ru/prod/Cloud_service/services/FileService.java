package ru.prod.Cloud_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.prod.Cloud_service.Entity_models.File;
import ru.prod.Cloud_service.Entity_models.User;
import ru.prod.Cloud_service.dto.FileDTO;
import ru.prod.Cloud_service.repositories.FileRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FileService {

    private final FileRepository fileRepository;

    private final AuthorizationService authorizationService;

    public List<FileDTO> getAllFiles(String authToken, int limit) {
        final User user = authorizationService.getUserByAuthToken(authToken);
        return fileRepository.findAllByUser(user).stream()
                .map(o -> new FileDTO(o.getFilename(), o.getSize()))
                .limit(limit)
                .collect(Collectors.toList());
    }


    public boolean addFile(String authToken, String filename, MultipartFile file) {
        final User user = authorizationService.getUserByAuthToken(authToken);


        try {
            fileRepository.save(new File(filename, file.getBytes(), file.getSize(), LocalDateTime.now(), user));
            return true;
        } catch (IOException e) {
            log.error("Upload file: Input data exception");
            throw new RuntimeException("Upload file: Input data exception");
        }

    }

    public void deleteFile(String authToken, String filename) {
        authorizationService.checkToken(authToken);
        fileRepository.deleteByFilename(filename);
        log.info("File {} delete", filename);
    }
}

package ru.prod.Cloud_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.prod.Cloud_service.Entity_models.File;
import ru.prod.Cloud_service.repositories.FileRepository;
import ru.prod.Cloud_service.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public boolean addFile(String authToken, String filename, MultipartFile file) {
        authorizationService.checkToken(authToken);
        // TODO save vile

        return true;
    }

}

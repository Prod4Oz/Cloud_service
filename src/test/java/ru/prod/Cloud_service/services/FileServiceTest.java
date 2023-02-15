package ru.prod.Cloud_service.services;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.prod.Cloud_service.Entity_models.File;
import ru.prod.Cloud_service.Entity_models.User;
import ru.prod.Cloud_service.dto.FileDTO;
import ru.prod.Cloud_service.repositories.FileRepository;
import ru.prod.Cloud_service.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileServiceTest {


    public static final String authToken = "Bearer Token";
    public static final User user = new User("user", "user", new ArrayList<>());
    public static final String filename = "filename";
    public static final String filename2 = "filename_old";
    public static final File file = new File(filename, filename.getBytes(),10L,  LocalDateTime.now(), user);
    public static final File file2 = new File(filename2, filename2.getBytes(),1L,  LocalDateTime.now(), user);
    public static final List<File> FILE_LIST = List.of(file, file2);

    public static final FileDTO fileDTO = new FileDTO(filename, 10L);
    public static final FileDTO fileDTO2 = new FileDTO(filename2, 1L);
    public static final List<FileDTO> FILE_DTO = List.of(fileDTO, fileDTO2);

    public static final MultipartFile multipart = new MockMultipartFile(filename, filename.getBytes());



    private FileRepository fileRepository = createFileRepositoryMock();


    private UserRepository userRepository = createUserRepositoryMock();

    AuthorizationService authorizationService = createAuthorizationServiceMock();

    private AuthorizationService createAuthorizationServiceMock() {
        final AuthorizationService authorizationService = Mockito.mock(AuthorizationService.class);
        when(authorizationService.getUserByAuthToken(authToken)).thenReturn(user);
        return authorizationService;
    }

    private FileRepository createFileRepositoryMock() {
        final FileRepository fileRepository = Mockito.mock(FileRepository.class);
        when(fileRepository.findAllByUser(user)).thenReturn(FILE_LIST);
        when(fileRepository.findByFilename(filename)).thenReturn(file);
        return fileRepository;
    }

    private UserRepository createUserRepositoryMock() {
        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findByUsername("user")).thenReturn(user);
        return userRepository;
    }



    @Test
    public void getAllFiles() {
        final FileService fileService = new FileService(fileRepository,authorizationService);
        assertEquals(FILE_DTO, fileService.getAllFiles(authToken, 10));
    }



    @Test
    public void addFile() {
        final FileService fileService = new FileService(fileRepository,authorizationService);
        assertTrue(fileService.addFile(authToken, filename, multipart));
    }

    @Test
    public void deleteFile() {
        final FileService fileService = new FileService(fileRepository,authorizationService);
        fileService.deleteFile(authToken, filename);
        Mockito.verify(fileRepository, Mockito.times(1)).deleteByFilename(filename);
    }

    @Test
    public void renameFile() {
        final FileService fileService = new FileService(fileRepository,authorizationService);
        fileService.renameFile(authToken,filename2,filename);
        Mockito.verify(fileRepository, Mockito.times(1)).editFileNameByUser(user,filename2,filename);
    }

    @Test
    public void downloadFile() {
       final FileService fileService = new FileService(fileRepository,authorizationService);
        assertEquals(filename, fileService.downloadFile(authToken,filename).getFilename());
    }
}
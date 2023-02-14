package ru.prod.Cloud_service.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.prod.Cloud_service.dto.RenameFile;
import ru.prod.Cloud_service.services.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<?> upload(@RequestHeader("auth-token")
                                    @NotBlank String authToken, @NotBlank String filename,
                                    @NotNull @RequestParam MultipartFile file) {

        fileService.addFile(authToken, filename, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String filename) {
        fileService.deleteFile(authToken, filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> renameFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String filename,
                                        @RequestBody RenameFile renameFile) {
        fileService.renameFile(authToken, filename, String.valueOf(renameFile));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
package ru.prod.Cloud_service.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.prod.Cloud_service.dto.FileDTO;
import ru.prod.Cloud_service.services.FileService;

import java.util.List;

@RestController
@RequestMapping("/list")
@AllArgsConstructor
public class FileListController {

    private final FileService fileService;

    @GetMapping
    public List<FileDTO> getAllFiles(@RequestHeader("auth-token") String authToken, @RequestParam("limit") int limit) {
        return fileService.getAllFiles(authToken, limit);
    }
}

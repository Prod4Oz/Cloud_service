package ru.prod.Cloud_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDTO {

    private String filename;
    private Long size;
}

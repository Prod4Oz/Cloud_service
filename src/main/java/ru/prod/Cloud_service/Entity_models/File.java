package ru.prod.Cloud_service.Entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name = "file")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    @Lob
    private byte[] fileContent;

    private Long size;

    @Column(nullable = false)
    private LocalDate uploadDate;
}

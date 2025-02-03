package com.example.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/down")
public class DownloadController {

    @GetMapping("/downloadExcel")
    public ResponseEntity<InputStreamResource> downloadExcel() {
        String filePath = "C:\\Reports\\TestDetails.xlsx";
        File file = new File(filePath);

        if (!file.exists()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=TestDetails.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(fileInputStream));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to download a file: " + e);
        }
    }
}

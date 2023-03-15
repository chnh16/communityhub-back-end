package com.lawencon.community.controller;

import java.util.Base64;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.File;
import com.lawencon.community.service.FileService;


@RestController
@RequestMapping("files")
public class FileController {

	private final FileService fileService;

	public FileController(final FileService fileService) {
		this.fileService = fileService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") final String id) {
		final Optional<File> file = fileService.getById(id);
		final String fileName = "attachment";
		final byte[] fileBytes = Base64.getDecoder().decode(file.get().getFileContent());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + "." + file.get().getFileExtension()).body(fileBytes);
	}

}
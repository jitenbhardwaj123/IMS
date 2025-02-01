package com.oak.service.impl;

import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.StringUtils.defaultString;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.oak.common.exception.model.EmptyFileReceivedException;
import com.oak.common.exception.model.FileNotReceivedException;
import com.oak.dto.FileResourceDto;

public class FileResourceUtils {
	public static FileResourceDto fromMultiPartFile(MultipartFile mpFile) {
		if (mpFile == null) {
			throw new FileNotReceivedException("No file was received.");
		}
		if (mpFile.isEmpty()) {
			throw new EmptyFileReceivedException("File Resource must have data.");
		}
		FileResourceDto dto = new FileResourceDto();
		try {
			dto.setRawData(mpFile.getBytes());
		} catch (IOException e) {
			throw new FileNotReceivedException("No file was received.");
		}
		dto.setFileName(defaultString(getBaseName(mpFile.getOriginalFilename())));
		dto.setFileExtension(defaultString(getExtension(mpFile.getOriginalFilename())));
		dto.setFileSize(mpFile.getSize());
		dto.setHttpContentType(mpFile.getContentType());
		return dto;
    }
}

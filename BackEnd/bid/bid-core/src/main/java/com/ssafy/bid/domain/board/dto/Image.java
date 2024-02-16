package com.ssafy.bid.domain.board.dto;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ssafy.bid.domain.board.service.ImageUploader;
import com.ssafy.bid.global.util.Preconditions;

import lombok.Getter;

@Getter
public class Image {

	/* 허용되는 파일 확장자 목록 */
	private static final List<String> ACCEPTED_FILE_EXTENSIONS = List.of("png", "webp", "jpg", "jpeg", "gif", "bmp",
		"svg");
	private final InputStream inputStream;
	private final String extension;
	private final String originalFilename;

	public Image(
		InputStream inputStream,
		String originalFilename
	) {
		this.inputStream = inputStream;
		this.extension = checkAndGetExtension(originalFilename);
		this.originalFilename = originalFilename;
	}

	private String checkAndGetExtension(String filename) {
		String extension = StringUtils.substringAfterLast(filename, ".");
		Preconditions.checkContains(extension, ACCEPTED_FILE_EXTENSIONS, "잘못된 파일 형식");
		return extension;
	}

	public String uploadBy(ImageUploader imageUploader) {
		return imageUploader.upload(this);
	}
}

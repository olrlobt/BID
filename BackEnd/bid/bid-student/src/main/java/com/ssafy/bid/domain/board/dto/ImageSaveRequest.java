package com.ssafy.bid.domain.board.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.bid.global.error.exception.InvalidActionException;

import lombok.Getter;

@Getter
public class ImageSaveRequest {
	private Image image;

	public ImageSaveRequest(MultipartFile file) {
		Image img = new Image(getInputStream(file), getOriginalFilename(file));
		this.image = img;
	}

	private InputStream getInputStream(MultipartFile file) {
		ByteArrayInputStream byteArrayInputStream;
		try {
			byte[] byteArray = file.getBytes();
			byteArrayInputStream = new ByteArrayInputStream(byteArray);
		} catch (IOException e) {
			throw new InvalidActionException("파일변환 과정에서 에러발생.");
		}
		return byteArrayInputStream;
	}

	private String getOriginalFilename(MultipartFile file) {
		return file.getOriginalFilename();
	}
}

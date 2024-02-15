package com.ssafy.bid.infra.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.bid.domain.board.dto.Image;
import com.ssafy.bid.domain.board.service.ImageUploader;
import com.ssafy.bid.global.error.exception.InvalidActionException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3ImageUploader implements ImageUploader {

	private final AmazonS3Client amazonS3Client;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Override
	public String upload(Image image) {
		File file = convert(image)
			.orElseThrow(() -> new InvalidActionException("파일 잘못됨."));

		String filename = UUID.randomUUID().toString();
		String imageUrl = putS3(file, filename);
		deleteFile(file);
		return imageUrl;
	}

	private Optional<File> convert(Image image) {
		File file = new File("/tmp/" + image.getOriginalFilename());
		try {
			if (file.createNewFile()) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					fos.write(image.getInputStream().readAllBytes());
				}
			}
		} catch (IOException e) {
			throw new InvalidActionException("파일 잘못됨.");
		}
		return Optional.of(file);
	}

	private String putS3(File file, String filename) {
		amazonS3Client.putObject(
			new PutObjectRequest(bucket, filename, file).withCannedAcl(
				CannedAccessControlList.PublicRead)
		);
		return amazonS3Client.getUrl(bucket, filename).toString();
	}

	private void deleteFile(File file) {
		try {
			file.delete();
		} catch (IllegalArgumentException e) {
			throw new InvalidActionException("파일 잘못됨.");
		}
	}
}

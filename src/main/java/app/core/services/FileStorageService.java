package app.core.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	@Value("${file.upload-dir}")
	private String storageDir;
	private Path fileStoragePath;

	@PostConstruct
	public void init() {

		this.fileStoragePath = Paths.get(this.storageDir).toAbsolutePath();
		try {
			Files.createDirectories(fileStoragePath);
		} catch (IOException e) {
			throw new RuntimeException("Could not create directory", e);
		}

	}

	public String storeFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (fileName.contains("..")) {
			throw new RuntimeException("File name contains illegal characters");
		}

		try {
			Path targetLocation = this.fileStoragePath.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {

			throw new RuntimeException("Storing file " + fileName + " failed." + e);
		}
		System.out.println(file.getOriginalFilename());
		return file.getOriginalFilename();

	}

}

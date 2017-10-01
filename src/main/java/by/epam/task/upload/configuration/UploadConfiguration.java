package by.epam.task.upload.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import by.epam.task.upload.api.FileProcessor;
import by.epam.task.upload.api.FileUploader;
import by.epam.task.upload.api.UploadQueue;
import by.epam.task.upload.local.LocalFileProcessor;
import by.epam.task.upload.local.LocalFileUploader;
import by.epam.task.upload.local.LocalUploadQueue;

@Configuration
public class UploadConfiguration
{
	@Bean
	public FileProcessor fileProcessor()
	{
		return new LocalFileProcessor(uploadQueue());
	}

	@Bean
	public FileUploader fileUploader()
	{
		return new LocalFileUploader(uploadQueue());
	}

	@Bean
	public UploadQueue uploadQueue()
	{
		return new LocalUploadQueue();
	}
}

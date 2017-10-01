package by.epam.task.upload.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import by.epam.task.upload.api.FileUploader;
import by.epam.task.upload.api.UploadMessage;
import by.epam.task.upload.api.UploadParameters;
import by.epam.task.upload.api.UploadQueue;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

public class LocalFileUploader implements FileUploader
{
	private final static Logger LOGGER = LoggerFactory.getLogger(LocalFileUploader.class);

	private final UploadQueue queue;

	public LocalFileUploader(UploadQueue queue)
	{
		this.queue = queue;
	}

	@Value("${upload.directory}")
	private String directory;
	@Value("${upload.key}")
	private String uploadKey;

	@PostConstruct
	private void init()
	{
		LOGGER.info("... File uploader was initialized with directory {} ...", directory);
	}

	@Override
	public void uploadFile(InputStream inputStream, UploadParameters uploadParameters)
	{
		String fileName = generateFileName();
		Collection<Job> jobs = prepareJobs(inputStream, uploadParameters, fileName);
		jobs.forEach(Job::doJob);

		queue.addMessage(new UploadMessage(fileName));
	}

	private Collection<Job> prepareJobs(InputStream inputStream, UploadParameters parameters, String fileName)
	{
		Collection<Job> jobs = new ArrayList<>();
		jobs.add(new UploadFileJob(inputStream, directory, fileName));
		if (parameters.isEncrypt())
		{
			LOGGER.info("... Encryption is enabled ...");
			jobs.add(new EncryptJob(uploadKey, directory, fileName));
		}
		if (parameters.isCompress())
		{
			LOGGER.info("... Compressing is enabled ...");
			jobs.add(new CompressFileJob(directory, fileName));
		}
		return jobs;
	}

	private String generateFileName()
	{
		return Thread.currentThread().getName() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-SS"));
	}

}

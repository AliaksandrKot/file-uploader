package by.epam.task.upload.local;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadFileJob implements Job
{
	private final static Logger LOGGER = LoggerFactory.getLogger(UploadFileJob.class);

	private final InputStream inputStream;
	private final String directory;
	private final String fileName;

	UploadFileJob(InputStream inputStream, String directory, String fileName)
	{
		this.inputStream = inputStream;
		this.directory = directory;
		this.fileName = fileName;
	}

	@Override
	public void doJob()
	{
		LOGGER.info("... Start uploading file with name = {} to directory = {} ...", fileName, directory);
		try
		{
			Path directoryPath = Paths.get(directory);
			if (!Files.isDirectory(directoryPath))
			{
				LOGGER.info("... Directory {} does not exist, creating directory ...", directory);
				Files.createDirectories(directoryPath);
			}
			String filePath = directory + fileName;
			Path path = Paths.get(filePath);
			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
			IOUtils.closeQuietly(inputStream);
			LOGGER.info("... File {} was successfully uploaded ... ", filePath);
		}
		catch (IOException e)
		{
			LOGGER.error(e.getMessage(), e);
		}
	}
}

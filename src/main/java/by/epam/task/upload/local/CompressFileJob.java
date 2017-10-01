package by.epam.task.upload.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

public class CompressFileJob implements Job
{
	private Logger LOGGER = LoggerFactory.getLogger(CompressFileJob.class);

	private final String fileName;
	private final String directory;
	private final static int BUFFER_SIZE = 1024;
	private final static String GZIP_EXTENSION = ".gzip";

	CompressFileJob(String directory, String fileName)
	{
		this.directory = directory;
		this.fileName = fileName;
	}

	@Override
	public void doJob()
	{
		byte[] buffer = new byte[BUFFER_SIZE];

		try
		{
			String filePath = directory + fileName;
			String compressedFilePath = directory + fileName + GZIP_EXTENSION;
			LOGGER.info("... Compression of file {} was started ...", filePath);
			FileOutputStream fileOutputStream = new FileOutputStream(compressedFilePath);

			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);

			FileInputStream fileInput = new FileInputStream(filePath);

			int bytes_read;

			while ((bytes_read = fileInput.read(buffer)) > 0)
			{
				gzipOutputStream.write(buffer, 0, bytes_read);
			}

			fileInput.close();

			gzipOutputStream.finish();
			gzipOutputStream.close();
			Files.delete(Paths.get(filePath));

			LOGGER.info("... File was successfully compressed to {} ...", compressedFilePath);

		}
		catch (IOException e)
		{
			LOGGER.error(e.getMessage(), e);
		}
	}
}

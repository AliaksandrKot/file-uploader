package by.epam.task.upload.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.crypto.Cipher;

public class EncryptJob extends CryptoJob implements Job
{
	private Logger LOGGER = LoggerFactory.getLogger(EncryptJob.class);

	private final static String ENCRYPT_PREFIX = "-enc";
	private final String key;
	private final String directory;
	private final String fileName;

	EncryptJob(String key, String directory, String fileName)
	{
		this.key = key;
		this.directory = directory;
		this.fileName = fileName;
	}

	@Override
	public void doJob()
	{
		String path = directory + fileName;
		LOGGER.info("... Encryption of file {} was started ...", path);
		String encryptPath = directory + fileName + ENCRYPT_PREFIX;
		try
		{
			doCrypto(Cipher.ENCRYPT_MODE, key, new File(path), new File(encryptPath));
			Path source = Paths.get(encryptPath);
			Path target = Paths.get(path);
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			Files.delete(source);
			LOGGER.info("... Encryption of file {} was successfully finished  ...", path);
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
		}
	}


}

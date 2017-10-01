package by.epam.task.upload.api;

import java.util.Objects;

public class UploadMessage
{
	private final String fileName;

	public UploadMessage(String fileName)
	{
		if (Objects.isNull(fileName))
		{
			throw new IllegalArgumentException("file name cannot be null");
		}
		this.fileName = fileName;
	}

	public String getFileName()
	{
		return fileName;
	}
}

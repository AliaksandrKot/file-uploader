package by.epam.task.upload.api;

import java.io.InputStream;

public interface FileUploader
{
	void uploadFile(InputStream inputStream, UploadParameters uploadParameters);
}

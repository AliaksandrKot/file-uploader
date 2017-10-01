package by.epam.task.upload.api;

import java.util.Optional;

public interface UploadQueue
{
	void addMessage(UploadMessage uploadMessage);

	Optional<UploadMessage> getMessage();
}

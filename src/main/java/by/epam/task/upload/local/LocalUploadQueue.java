package by.epam.task.upload.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.task.upload.api.UploadMessage;
import by.epam.task.upload.api.UploadQueue;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LocalUploadQueue implements UploadQueue
{
	private final Logger LOGGER = LoggerFactory.getLogger(LocalUploadQueue.class);
	private final ConcurrentLinkedQueue<UploadMessage> queue = new ConcurrentLinkedQueue<>();
	private final Object $lock = new Object();

	@Override
	public void addMessage(UploadMessage uploadMessage)
	{
		synchronized ($lock)
		{
			if (Objects.nonNull(uploadMessage))
			{
				LOGGER.info("... File with name {} was added to queue for processing ...", uploadMessage.getFileName());
				queue.add(uploadMessage);
			}
		}
	}

	@Override
	public Optional<UploadMessage> getMessage()
	{
		synchronized ($lock)
		{
			return Optional.ofNullable(queue.poll());
		}
	}
}

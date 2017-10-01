package by.epam.task.upload.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.task.upload.api.FileProcessor;
import by.epam.task.upload.api.UploadMessage;
import by.epam.task.upload.api.UploadQueue;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

public class LocalFileProcessor implements FileProcessor
{
	private Logger LOGGER = LoggerFactory.getLogger(LocalFileProcessor.class);

	private final UploadQueue uploadQueue;
	private final static long sleepTime = 1000L;

	public LocalFileProcessor(UploadQueue uploadQueue)
	{
		this.uploadQueue = uploadQueue;
	}

	@PostConstruct
	private void initialize()
	{
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(new Processor(uploadQueue));
	}

	private class Processor implements Runnable
	{

		private final UploadQueue uploadQueue;

		private Processor(UploadQueue uploadQueue)
		{
			this.uploadQueue = uploadQueue;
		}

		@Override
		public void run()
		{
			boolean isInterrupt = false;
			while (!isInterrupt)
			{
				Optional<UploadMessage> message = uploadQueue.getMessage();
				if (message.isPresent())
				{
					LOGGER.info("... Processing file with name {} ...", message.get().getFileName());
				}
				else
				{
					try
					{
						Thread.sleep(sleepTime);
					}
					catch (InterruptedException e)
					{
						isInterrupt = true;
						LOGGER.error(e.getMessage(), e);
					}
				}

			}

		}
	}
}

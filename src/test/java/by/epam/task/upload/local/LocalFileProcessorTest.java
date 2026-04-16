package by.epam.task.upload.local;

import by.epam.task.upload.api.UploadMessage;
import by.epam.task.upload.api.UploadQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocalFileProcessorTest
{
	@Mock
	private UploadQueue uploadQueue;

	@Test
	public void constructorAcceptsUploadQueue()
	{
		LocalFileProcessor processor = new LocalFileProcessor(uploadQueue);
		assertNotNull(processor);
	}

	@Test
	public void processorPollsQueueAfterInitialization() throws Exception
	{
		CountDownLatch latch = new CountDownLatch(1);

		when(uploadQueue.getMessage()).thenAnswer(invocation -> {
			latch.countDown();
			Thread.sleep(Long.MAX_VALUE);
			return Optional.empty();
		});

		LocalFileProcessor processor = new LocalFileProcessor(uploadQueue);
		invokeInitialize(processor);

		assertTrue("Processor should poll queue within 2 seconds", latch.await(2, TimeUnit.SECONDS));
		verify(uploadQueue, atLeastOnce()).getMessage();
	}

	@Test
	public void processorHandlesMessageFromQueue() throws Exception
	{
		CountDownLatch latch = new CountDownLatch(1);
		UploadMessage message = new UploadMessage("test-file.txt");

		when(uploadQueue.getMessage())
			.thenReturn(Optional.of(message))
			.thenAnswer(invocation -> {
				latch.countDown();
				Thread.sleep(Long.MAX_VALUE);
				return Optional.empty();
			});

		LocalFileProcessor processor = new LocalFileProcessor(uploadQueue);
		invokeInitialize(processor);

		assertTrue("Processor should finish handling message within 2 seconds", latch.await(2, TimeUnit.SECONDS));
		verify(uploadQueue, atLeastOnce()).getMessage();
	}

	@Test
	public void processorSleepsWhenQueueIsEmpty() throws Exception
	{
		CountDownLatch secondPollLatch = new CountDownLatch(1);

		when(uploadQueue.getMessage())
			.thenReturn(Optional.empty())
			.thenAnswer(invocation -> {
				secondPollLatch.countDown();
				Thread.sleep(Long.MAX_VALUE);
				return Optional.empty();
			});

		LocalFileProcessor processor = new LocalFileProcessor(uploadQueue);
		invokeInitialize(processor);

		assertTrue("Processor should re-poll queue after sleep within 3 seconds",
			secondPollLatch.await(3, TimeUnit.SECONDS));
		verify(uploadQueue, atLeastOnce()).getMessage();
	}

	private void invokeInitialize(LocalFileProcessor processor) throws Exception
	{
		Method initialize = LocalFileProcessor.class.getDeclaredMethod("initialize");
		initialize.setAccessible(true);
		initialize.invoke(processor);
	}
}

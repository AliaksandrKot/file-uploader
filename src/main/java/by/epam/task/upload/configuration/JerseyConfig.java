package by.epam.task.upload.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import by.epam.task.upload.web.UploadEndpoint;

@Component
public class JerseyConfig extends ResourceConfig
{
	public JerseyConfig()
	{
		register(UploadEndpoint.class);
	}
}

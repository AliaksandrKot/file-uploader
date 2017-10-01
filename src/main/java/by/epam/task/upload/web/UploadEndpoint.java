package by.epam.task.upload.web;

import org.springframework.stereotype.Component;

import by.epam.task.upload.api.FileUploader;
import by.epam.task.upload.api.UploadParameters;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("upload")
@Component
public class UploadEndpoint
{
	private final FileUploader fileUploader;

	public UploadEndpoint(FileUploader fileUploader)
	{
		this.fileUploader = fileUploader;

	}

	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(InputStream inputStream, @QueryParam("isCompress") boolean isCompress, @QueryParam("isEncrypt") boolean isEncrypt)
	{
		fileUploader.uploadFile(inputStream, new UploadParameters(isCompress, isEncrypt));
		return Response.ok().build();
	}
}

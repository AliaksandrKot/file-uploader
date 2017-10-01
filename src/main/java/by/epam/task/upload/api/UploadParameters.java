package by.epam.task.upload.api;

public class UploadParameters
{
	private final boolean isCompress;
	private final boolean encrypt;

	public UploadParameters(boolean isCompress, boolean encrypt)
	{
		this.isCompress = isCompress;
		this.encrypt = encrypt;
	}

	public boolean isCompress()
	{
		return isCompress;
	}

	public boolean isEncrypt()
	{
		return encrypt;
	}
}

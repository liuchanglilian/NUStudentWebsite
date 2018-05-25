package com.my.spring.exception;

public class PostException extends Exception{
	public PostException(String message)
	{
		super("PostException-"+message);
	}
	
	public PostException(String message, Throwable cause)
	{
		super("PostException-"+message,cause);
	}
}

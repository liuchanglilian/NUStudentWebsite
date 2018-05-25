package com.my.spring.exception;

public class MessageException extends Exception {

	public MessageException(String message)
	{
		super("MessageException-"+message);
	}
	
	public MessageException(String message, Throwable cause)
	{
		super("MessageException-"+message,cause);
	}
	
}

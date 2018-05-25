package com.my.spring.exception;
public class BoardException extends Exception {
	
	public BoardException(String message)
	{
		super("BoardException-"+ message);
	}
	
	public BoardException(String message, Throwable cause)
	{
		super("BoardException-"+ message,cause);
	}

}
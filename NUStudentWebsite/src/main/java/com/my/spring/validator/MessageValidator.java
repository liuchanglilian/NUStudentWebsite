package com.my.spring.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.spring.pojo.Board;
import com.my.spring.pojo.Message;

@Component
public class MessageValidator implements Validator {
	public boolean supports(Class aClass) {
		return aClass.equals(Message.class);
	}
	public void validate(Object obj, Errors errors) {
		Message message = (Message) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "error.invalid.message", "Message Content Required");
		
	}
	
}

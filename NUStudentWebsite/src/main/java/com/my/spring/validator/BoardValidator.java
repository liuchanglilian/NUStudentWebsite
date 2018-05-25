package com.my.spring.validator;

import org.springframework.validation.Validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.my.spring.pojo.Board;
import com.my.spring.pojo.User;

@Component
public class BoardValidator implements Validator {


	public boolean supports(Class aClass) {
		return aClass.equals(Board.class);
	}
	
	public void validate(Object obj, Errors errors) {
		Board board = (Board) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "boardName", "error.invalid.board", "Board Name Required");
		
	}
	
}

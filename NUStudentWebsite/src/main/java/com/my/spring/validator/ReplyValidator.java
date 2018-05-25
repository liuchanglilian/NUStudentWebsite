package com.my.spring.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.my.spring.pojo.Reply;

public class ReplyValidator implements Validator{
	public boolean supports(Class aClass) {
		return aClass.equals(Reply.class);
	}
	
	public void validate(Object obj, Errors errors) {
		Reply reply = (Reply) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "error.invalid.reply", "Reply content Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postId", "error.invalid.reply", "Reply postId Required");
		
	}

}

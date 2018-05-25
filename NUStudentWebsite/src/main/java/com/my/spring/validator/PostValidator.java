package com.my.spring.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.spring.pojo.Board;
import com.my.spring.pojo.Post;
@Component
public class PostValidator implements Validator{
	public boolean supports(Class aClass) {
		return aClass.equals(Post.class);
	}
	
	public void validate(Object obj, Errors errors) {
		Post post = (Post) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "error.invalid.title", "title Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "error.invalid.text", "content Required");
	
	
	}
}

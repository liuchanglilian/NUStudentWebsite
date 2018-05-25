package com.my.spring.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.spring.pojo.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;
public class UserValidator implements Validator {
   
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
			   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";  
	private Pattern pattern= Pattern.compile(EMAIL_PATTERN); 
	private Matcher matcher;
	public boolean supports(Class aClass) {
		return aClass.equals(User.class);
	}

	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
	   matcher = pattern.matcher(user.getUserEmail());
	   if (!matcher.matches()) {  
		    errors.rejectValue("userEmail", "error.invalid.email.userEmail",  
		      "Enter a correct email");  
		   }  
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail", "error.invalid.email.userEmail", "User Email Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "error.invalid.password", "Password Required");
		// check if user exists
		
	}
}

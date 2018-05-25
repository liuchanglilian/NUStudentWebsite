package com.my.spring.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.dao.UserDAO;

@Controller
public class AjaxController {
	
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@RequestMapping(value = "/ajaxservice.htm", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxService(HttpServletRequest request)
	{
		System.out.println("reach here");
		String queryString = request.getParameter("email");
		System.out.println("email ajax testing is "+queryString);
		
		boolean result=userDao.get(queryString)==null;
		
		if(result) 
			{
			   System.out.println("no");
			   return "no";}
		else 
		{
			System.out.println("yes");
			return "exist";
		}
	}

}

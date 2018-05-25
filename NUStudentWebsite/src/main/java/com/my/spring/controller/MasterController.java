package com.my.spring.controller;

import java.util.List;

import javax.inject.Qualifier;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.my.spring.dao.BoardDAO;
import com.my.spring.dao.UserDAO;
import com.my.spring.exception.BoardException;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Board;
import com.my.spring.pojo.User;
import com.my.spring.validator.BoardValidator;
@Controller
@RequestMapping("/master")
public class MasterController {
    
	@Autowired
	UserDAO userDao;
	
	@Autowired
	 BoardDAO boardDao;	
	
	@Autowired
	BoardValidator boardValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(boardValidator);
	}
	@RequestMapping(value="/addboard.htm", method = RequestMethod.GET)
	public String addBoard(HttpServletRequest request,ModelMap map) {
	  try {
		List<Board> boards=boardDao.list();
		map.addAttribute("boardList",boards);
		map.addAttribute("board",new Board());
	} catch (BoardException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "error";
	}
	  
       return "add-board";
	}
	@RequestMapping(value="/addboard.htm", method = RequestMethod.POST)
	public String addBoardTotable(@ModelAttribute("board") Board board, BindingResult result,HttpServletRequest request,ModelMap map) {
		boardValidator.validate(board, result);
	    if (result.hasErrors()) {
	    	map.addAttribute("board", board);
			return "add-board";
		}

		board.setPostNumber(0);
		try {
			boardDao.addBoard(board);
			List<Board> boards=boardDao.list();
			map.addAttribute("boardList",boards);
		} catch (BoardException e) {
			map.addAttribute("errorMessage", "adding map failed");
			return "error";
		}
       return "add-board";
	}
	@RequestMapping(value="/lockaccount.htm", method = RequestMethod.GET)
	public String lockUser(HttpServletRequest request, ModelMap map) {
		Long userId=Long.parseLong(request.getParameter("userId"));
		try {
			User user=userDao.lockUser(userId);
			sendEmail(user.getUserEmail(), "According to your performance you accout has been locked");
			List<User> list=userDao.selectAll();
			map.addAttribute("userList", list);
		} catch (UserException e) {
			map.addAttribute("errorMessage", "adding user list failed");
			return "error";
		}
		
       return "manage-user";
	}
	
	@RequestMapping(value="/unlockaccount.htm", method = RequestMethod.GET)
	public String unlockUser(HttpServletRequest request,ModelMap map) {
		Long userId=Long.parseLong(request.getParameter("userId"));
		try {
			User user=userDao.unlockUser(userId);
			sendEmail(user.getUserEmail(), "Website manager Chang unlock your account");
			List<User> list=userDao.selectAll();
			map.addAttribute("userList", list);
		} catch (UserException e) {
			map.addAttribute("errorMessage", "adding user list failed");
			return "error";
		}
       return "manage-user";
	}


	@RequestMapping(value="/userlist.htm", method = RequestMethod.GET)
	public String manageUser(HttpServletRequest request,ModelMap map) {
		
		try {
			List<User> list=userDao.selectAll();
			map.addAttribute("userList", list);
			System.out.println("userlist length"+list.size());
		} catch (UserException e) {
			map.addAttribute("errorMessage", "adding user list failed");
			return "error";
		}
       return "manage-user";
	}
	
	public void sendEmail(String useremail, String message) {
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("contactapplication2018@gmail.com", "springmvc"));
			email.setSSLOnConnect(true);
			email.setFrom("no-reply@msis.neu.edu"); // This user email does not
													// exist
			email.setSubject("Password Reminder");
			email.setMsg(message); // Retrieve email from the DAO and send this
			email.addTo(useremail);
			email.send();
		} catch (EmailException e) {
			System.out.println("Email cannot be sent");
		}
	}
	
}

package com.my.spring.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.dao.MessageDAO;
import com.my.spring.dao.UserDAO;
import com.my.spring.exception.MessageException;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Message;
import com.my.spring.pojo.Post;
import com.my.spring.pojo.Reply;
import com.my.spring.pojo.User;
import com.my.spring.validator.MessageValidator;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	@Autowired
	@Qualifier("messageDao")
	MessageDAO messageDao;
     
	
	@RequestMapping(value = "/sendmessage.htm", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxService(HttpServletRequest request)
	{
		String message=request.getParameter("message");
		Long senderId=Long.parseLong(request.getParameter("senderid"));
		Long receiverId=Long.parseLong(request.getParameter("receiverid"));
		System.out.println("#################ajax controller:message is "+message);
		System.out.println("#################ajax controller:sender is "+senderId);
		System.out.println("#################ajax controller:receiver is "+receiverId);
		try {
			User sender=userDao.get(senderId);
			User receiver=userDao.get(receiverId);
			Message sendMessage=new Message();
			sendMessage.setDate(new Date());
			sendMessage.setSender(sender);
			sendMessage.setReceiver(receiver);
			sendMessage.setMessage(message);
			messageDao.save(sendMessage);
			return "yes";
		} catch (UserException e) {
			e.printStackTrace();
		}catch(MessageException e) {
			e.printStackTrace();
		}
		return "no";
		
	}
	@RequestMapping(value = "/getmessage.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getMessage(HttpServletRequest request)
	{   
		
		Long userId=Long.parseLong(request.getParameter("userid"));
		Message message=messageDao.getMessages(userId);
		if(message!=null)
		//String s="message:";
		{String s="";
		s=s+message.getMessage();
		s=s+":";
		s=s+message.getSender().getUserEmail();
		s=s+":";
		s=s+message.getMessageId();
		return s;
		}
		return "no message";
}
	@RequestMapping(value = "/readmessage.htm", method = RequestMethod.GET)
	@ResponseBody
	public String readMessage(HttpServletRequest request)
	{
		Long messageId=Long.parseLong(request.getParameter("messageid"));
		try {
			messageDao.setRead(messageId);
			return "true";
		} catch (MessageException e) {
			e.printStackTrace();
			return "false";
		}
		
}
}

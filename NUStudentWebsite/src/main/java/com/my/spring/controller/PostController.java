package com.my.spring.controller;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.my.spring.dao.PostDAO;
import com.my.spring.dao.UserDAO;
import com.my.spring.exception.PostException;
import com.my.spring.pojo.Post;
import com.my.spring.pojo.Reply;
import com.my.spring.pojo.User;
import com.my.spring.validator.ReplyValidator;

@Controller
@RequestMapping("/post")
public class PostController {

@Autowired
PostDAO postDao;

@Autowired
UserDAO userDao;

@Autowired
ReplyValidator replyValidator;

@InitBinder("reply")  
public void initBinderReply(WebDataBinder binder) {  
    binder.setFieldDefaultPrefix("reply.");  
    System.out.println("reply validator");
}  
	@RequestMapping(value="/postdetail.htm", method = RequestMethod.GET)
	public String viewDetail(HttpServletRequest request, ModelMap map) {
        try {
		long postId =Long.parseLong(request.getParameter("postId"));
		Post post=postDao.getPost(postId);
		
		Set<Reply> set=post.getReplies();
		map.addAttribute("post", post);
		map.addAttribute("replies",set);
		map.addAttribute("reply",new Reply());
		return "view-post";
        }catch(Exception e) {
        	map.addAttribute("errorMessage", e.getMessage());
        	return "error";
        }
	}
	   
	@RequestMapping(value="/viewpostOfuser.htm", method = RequestMethod.GET)
	public String viewPostOfUser(HttpServletRequest request, ModelMap map) {
        try {
		long userId =Long.parseLong(request.getParameter("userId"));
		User user=userDao.get(userId);
		List<Post> list=postDao.getAllPost(userId);
		map.addAttribute("user", user);
		System.out.println(user.getUserEmail());
		System.out.println("post size"+list.size());
		map.addAttribute("posts",list);
		return "all-posts";
        }catch(Exception e) {
        	map.addAttribute("errorMessage", e.getMessage());
        	return "error";
        }
	}
	   
	
        @RequestMapping(value="/addreply.htm", method = RequestMethod.POST)
    	public String addReply(@ModelAttribute("reply") Reply reply, HttpServletRequest request,BindingResult result,ModelMap map) {
        	replyValidator.validate(reply, result);
        	if (result.hasErrors()) {
        		map.addAttribute("reply", reply);
        		return "view-post";
        		
    		}
        	
        	try {
    		Long postId=reply.getPostId();
    		Post post=postDao.getPost(postId);
    		HttpSession session=request.getSession();
    		User user=(User)session.getAttribute("user");
    		if(user==null)
    			return "error";
    		User userInDatabase=userDao.get(user.getUserId());
    		reply.setPost(post);
    		reply.setUser(userInDatabase);
    		 post.getReplies().add(reply);
    	    post.setReplyNumber(post.getReplyNumber()+1);
    	    System.out.println("post title:"+post.getTitle());
    		postDao.update(post);
    		map.addAttribute("post", post);
    		Set<Reply> set=post.getReplies();
    		map.addAttribute("replies",set);
    		return "view-post";
            }catch(Exception e) {
            	map.addAttribute("errorMessage", e.getMessage());
            	return "error";
            }	
}
}

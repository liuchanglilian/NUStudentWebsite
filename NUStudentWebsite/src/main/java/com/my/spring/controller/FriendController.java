package com.my.spring.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.my.spring.dao.UserDAO;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.User;

@Controller
@RequestMapping("/friend")
public class FriendController {

	@RequestMapping(value = "/viewprofile.htm", method = RequestMethod.GET)
	public String showLoginForm(HttpServletRequest request, UserDAO userDao, ModelMap map) {

		try {
			Long userId = Long.parseLong(request.getParameter("userId"));
			User user = userDao.get(userId);
			map.addAttribute("viewUser", user);
			return "view-profile";
		} catch (UserException e) {
			e.printStackTrace();
			return "error";
		}

	}

	@RequestMapping(value = "/add.htm", method = RequestMethod.GET)
	public String addFriedn(HttpServletRequest request, UserDAO userDao, ModelMap map) {

		try {
			Long userId = Long.parseLong(request.getParameter("userId"));
			User user = userDao.get(userId);
			Set<User> friends = user.getFriends();
			HttpSession session = request.getSession();
			User sessionUser = (User) session.getAttribute("user");
			User persistentUser = userDao.get(sessionUser.getUserId());
			for (User u : friends) {
				if (u.getUserId() == sessionUser.getUserId())
					return "error";
			}
			persistentUser.getFriends().add(user);
			user.getFriends().add(persistentUser);
			try {
				userDao.update(persistentUser);
			} catch (Exception e) {
				map.addAttribute("errorMessage", "failed to add friends");
			}
			map.addAttribute("viewUser", user);
			return "view-profile";
		} catch (UserException e) {
			e.printStackTrace();
			return "error";
		}

	}

}

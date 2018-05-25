package com.my.spring.controller;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.captcha.botdetect.web.servlet.Captcha;
import com.my.spring.dao.BoardDAO;
import com.my.spring.dao.MessageDAO;
import com.my.spring.dao.PostDAO;
import com.my.spring.dao.UserDAO;
import com.my.spring.exception.BoardException;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Board;
import com.my.spring.pojo.Message;
import com.my.spring.pojo.Post;
import com.my.spring.pojo.User;
import com.my.spring.validator.MessageValidator;
import com.my.spring.validator.PostValidator;
import com.my.spring.validator.UserValidator;

import Tools.FileUploadUtil;
import Tools.ImageCut;

@Controller
public class UserController {
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("postDao")
	PostDAO postDao;

	@Autowired
	@Qualifier("userValidator")
	UserValidator userValidator;

	@Autowired
	@Qualifier("postValidator")
	PostValidator postValidator;

	@InitBinder("user")
	public void initBinderReply(WebDataBinder binder) {
		// binder.setFieldDefaultPrefix("user.");
		binder.setValidator(userValidator);
		System.out.println("user validation");
	}

	@InitBinder("post")
	public void initBinderPost(WebDataBinder binder) {
		// binder.setFieldDefaultPrefix("user.");
		binder.setValidator(postValidator);
		System.out.println("user validation");
	}

	@Autowired
	@Qualifier("boardDao")
	BoardDAO boardDao;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/signin.htm", method = RequestMethod.GET)
	public String showLoginForm(ModelMap map) {
		map.addAttribute("user", new User());
		return "user-create-form";
	}

	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/index.htm";
	}

	@RequestMapping(value = "/index.htm", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap map) {

		HttpSession session = request.getSession();
		Object uInSesssion = session.getAttribute("user");
		if (uInSesssion != null) {
			User user = (User) uInSesssion;
			try {
				User u = userDao.get(user.getUserEmail(), user.getUserPassword());
				System.out.println("friend length" + u.getFriends().size());
				if (u != null && u.getLocked() == 0) {
					if (u.getUserType().equals("master"))
						return "master-board";

				} else if (u != null && u.getLocked() == 1) {
					map.addAttribute("errorMessage", "Please activate your account to login!");
					return "error";
				} else if (u != null && u.getLocked() == 3) {

					map.addAttribute("errorMessage",
							"Sorry your account has been forbidened by the website manager! Please get connect with Chang");
					return "error";

				}
			} catch (Exception e) {
				return "error";
			}

		}
		List<Board> listOfBoard = null;
		try {
			listOfBoard = boardDao.list();
			System.out.println("size of list" + listOfBoard.size());
		} catch (Exception e) {
			return "error";
		}
		if (listOfBoard != null)
			map.addAttribute("boardList", listOfBoard);
		map.addAttribute("message", new Message());
		return "index";
	}

	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public String login() {
		return "user-login";
	}

	/**
	 * @param request
	 * @param userDao
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public String handleLoginForm(HttpServletRequest request, ModelMap map) {
		String username = request.getParameter("username");
		System.out.println("printing username" + username);
		String password = request.getParameter("password");
		System.out.println("printing password" + password);
		try {
			User u = userDao.get(username, password);
			if (u != null && u.getLocked() == 0) {

				request.getSession().setAttribute("user", u);
				System.out.println("usertype" + u.getUserType());
				if (u.getUserType().equals("master"))
					return "master-board";

				return "redirect:/index.htm";
			} else if (u != null && u.getLocked() == 1) {
				map.addAttribute("errorMessage", "Please activate your account to login!");
				return "error";
			} else if (u != null && u.getLocked() == 3) {

				map.addAttribute("errorMessage",
						"Sorry your account has been forbidened by the website manager! Please get connect with Chang");
				return "error";

			} else {
				map.addAttribute("errorMessage", "Invalid username/password!");
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping(value = "/user/create.htm", method = RequestMethod.GET)
	public String showCreateForm() {

		return "user-create-form";
	}

	@RequestMapping(value = "/user/create.htm", method = RequestMethod.POST)
	public String handleCreateForm(HttpServletRequest request, ModelMap map, @ModelAttribute("user") User user,
			BindingResult result,

			@RequestParam("imgFile") MultipartFile imgfile) {
		userValidator.validate(user, result);

		if (result.hasErrors()) {
			map.addAttribute("user", user);
			return "user-create-form";
		}

		Captcha captcha = Captcha.load(request, "captchaObject");
		String captchaCode = request.getParameter("captchaCode");
		HttpSession session = request.getSession();
		if (userDao.get(user.getUserEmail()) != null) {
			map.addAttribute("errorMessage", "user email already exist");
			return "error";
		}
		if (captcha.validate(captchaCode)) {

			try {
				String realPath = "/";
				String resourcePath = "photos/";

				String imagePath = realPath + resourcePath + "empty.png";
				String sqlPath = "/" + resourcePath + "empty.png";
				if (imgfile != null) {
					if (FileUploadUtil.allowUpload(imgfile.getContentType())) {
						String fileName = FileUploadUtil.rename(imgfile.getOriginalFilename());
						int end = fileName.lastIndexOf(".");
						String saveName = fileName.substring(0, end);
						File dir = new File(
								"C:/Users/liuch/Documents/workspace-sts-3.9.3.RELEASE/NU_Chino/src/main/webapp/photos/");
						if (!dir.exists()) {
							dir.mkdirs();
						}
						File file = new File(dir, saveName + "_src.jpg");
						imgfile.transferTo(file);
						String srcImagePath = realPath + resourcePath + saveName;
						sqlPath = "/" + resourcePath + saveName + "_cut.jpg";
						System.out.println("source path is:" + srcImagePath);
						String storePath = "C:/Users/liuch/Documents/workspace-sts-3.9.3.RELEASE/NU_Chino/src/main/webapp"
								+ srcImagePath;
						System.out.println("store path:" + storePath);
						String x = request.getParameter("x");
						String y = request.getParameter("y");
						String w = request.getParameter("w");
						String h = request.getParameter("h");
						System.out.println("x,y,w,h" + x + "," + y + "," + w + "," + h);
						int imageX = Integer.parseInt(x);
						int imageY = Integer.parseInt(y);
						int imageH = Integer.parseInt(h);
						int imageW = Integer.parseInt(w);
						System.out.println("==========imageCutStart=============");
						ImageCut.imgCut(storePath, imageX, imageY, imageW, imageH);
						System.out.println("==========imageCutEnd=============");
						System.out.println(srcImagePath);
						imagePath = srcImagePath + "_cut.jpg";
						System.out.println("image path is :   " + imagePath);

					}
				}
				user.setProfile(sqlPath);
				User u = userDao.register(user);

				Random rand = new Random();
				int randomNum1 = rand.nextInt(5000000);
				int randomNum2 = rand.nextInt(5000000);
				try {
					String str = "http://localhost:8080/lab8/user/validateemail.htm?email=" + user.getUserEmail()
							+ "&key1=" + randomNum1 + "&key2=" + randomNum2;
					session.setAttribute("key1", randomNum1);
					session.setAttribute("key2", randomNum2);
					sendEmail(user.getUserEmail(), "Click on this link to activate your account : " + str);
				} catch (Exception e) {
					System.out.println("Email cannot be sent");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			map.addAttribute("errorMessage", "Invalid Captcha!");
			return "user-create-form";
		}

		return "user-login";
	}

	@RequestMapping(value = "/user/forgotpassword.htm", method = RequestMethod.GET)
	public String getForgotPasswordForm(HttpServletRequest request) {

		return "forgot-password";
	}

	@RequestMapping(value = "/user/forgotpassword.htm", method = RequestMethod.POST)
	public String handleForgotPasswordForm(HttpServletRequest request, UserDAO userDao) {

		String useremail = request.getParameter("useremail");
		Captcha captcha = Captcha.load(request, "CaptchaObject");
		String captchaCode = request.getParameter("captchaCode");

		if (captcha.validate(captchaCode)) {
			User user = userDao.get(useremail);
			sendEmail(useremail, "Your password is : " + user.getUserPassword());
			return "forgot-password-success";
		} else {
			request.setAttribute("captchamsg", "Captcha not valid");
			return "forgot-password";
		}
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

	@RequestMapping(value = "/user/addpost.htm", method = RequestMethod.GET)
	public String viewBoard(HttpServletRequest request, ModelMap map) {
		System.out.println("hahhahahha huhuhuhu");
		try {
			long boardId = Long.parseLong(request.getParameter("boardId"));
			List<Post> postList = postDao.get(boardId);
			map.addAttribute("boardId", boardId);
			map.addAttribute("postList", postList);
			map.addAttribute("post", new Post());
			return "view-board";
		} catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "user/validateemail.htm", method = RequestMethod.GET)
	public String validateEmail(HttpServletRequest request, ModelMap map) {

		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		int key1 = Integer.parseInt(request.getParameter("key1"));
		int key2 = Integer.parseInt(request.getParameter("key2"));
		System.out.println("I am printing key1 and key2");
		System.out.println(session.getAttribute("key1"));
		System.out.println(session.getAttribute("key2"));
		int k1 = 0;
		int k2 = 0;
		try {
			k1 = (Integer) session.getAttribute("key1");
			k2 = (Integer) session.getAttribute("key2");
		} catch (Exception e) {
			System.out.println("don't have key in session");
			return "error";
		}
		if (k1 == key1 && key2 == key2) {
			try {
				System.out.println("HI________");
				boolean updateStatus = userDao.updateUser(email);
				if (updateStatus) {
					return "user-login";
				} else {
					System.out.println("don't know why can't update");
					return "error";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("k1=" + k1);
			System.out.println("k2=" + k2);
			map.addAttribute("errorMessage", "Link expired , generate new link");
			map.addAttribute("resendLink", true);
			return "error";
		}

		return "user-login";

	}

	@RequestMapping(value = "/user/newpost.htm", method = RequestMethod.POST)
	public String addPost(HttpServletRequest request, @ModelAttribute("post") Post post, BindingResult result,
			ModelMap map) {

		postValidator.validate(post, result);

		if (result.hasErrors()) {
			map.addAttribute("post", post);
			return "view-board";
		}

		int boardId = Integer.parseInt(request.getParameter("boardId"));
		HttpSession session = request.getSession();
		Object u = session.getAttribute("user");
		if (u == null)
			return "user-login";
		try {
			User user = (User) u;
			User userInDatabase = userDao.get(user.getUserId());
			post.setUser(userInDatabase);
			System.out.println("1");
			Board board = boardDao.get(boardId);
			System.out.println(" *****************************board name: " + board.getBoardName());
			board.setPostNumber(board.getPostNumber() + 1);
			System.out.println("2");
			board.getPosts().add(post);
			post.setBoard(board);
			System.out.println("size of posts in board" + board.getPosts().size());
			boardDao.update(board);
			System.out.println("3");
			userInDatabase.getPosts().add(post);
			userInDatabase.setCredit(userInDatabase.getCredit() + 10);
			System.out.println("4");
			userDao.update(userInDatabase);
			List<Post> postList = postDao.get(boardId);
			map.addAttribute("boardId", boardId);
			map.addAttribute("postList", postList);

		} catch (BoardException boardException) {
			System.out.println("5");
			map.addAttribute("errorMessage", "caught board exception");
			return "error";
		} catch (UserException userException) {
			System.out.println("6");
			userException.printStackTrace();
			map.addAttribute("errorMessage", userException.getMessage());
			return "error";
		} catch (Exception e) {
			return "error";
		}
		return "view-board";
	}

}

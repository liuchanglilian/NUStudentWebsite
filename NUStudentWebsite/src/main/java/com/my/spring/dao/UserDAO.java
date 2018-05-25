package com.my.spring.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.spring.exception.BoardException;
import com.my.spring.exception.PostException;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Board;
import com.my.spring.pojo.Post;
import com.my.spring.pojo.User;


public class UserDAO extends DAO {

	public UserDAO() {
	}

	public User get(String username, String password) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where userEmail = :username and userPassword = :password");
			q.setString("username", username);
			q.setString("password", password);			
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}
	public User get(String userEmail){
		try {
			begin();
			Query q = getSession().createQuery("from User where userEmail = :useremail");
			q.setString("useremail", userEmail);
			User user = (User) q.uniqueResult();
			return user;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
			return null;
		
	}
	

	public User get(long userId) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where userID= :personID");
			q.setLong("personID", userId);		
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
			throw new UserException("Could not get user " + userId, e);
			
		}
	}
	
	public User register(User u)
			throws UserException {
		try {
			begin();
			System.out.println("inside DAO");

			
			User user = new User(u.getUserEmail(), u.getUserPassword());
            user.setProfile(u.getProfile());
			
			getSession().save(user);
			commit();
			return user;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}

	public void delete(User user) throws UserException {
		try {
			begin();
			getSession().delete(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not delete user " + user.getUserEmail(), e);
		}
	}

	public boolean updateUser(String email) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			System.out.println("searching for email"+email);
			Query q = getSession().createQuery("from User where userEmail = :useremail");
			q.setString("useremail", email);
			User user = (User) q.uniqueResult();
			if(user!=null){
				user.setLocked(0);
				System.out.println("I have already set it false");
				getSession().update(user);
				commit();
				return true;
			}else{
				System.out.println("can't get the user");
				return false;
			}

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating user: " + e.getMessage());
		}
	
	}
	public User lockUser(Long userId) throws UserException{
		try {
			begin();
			Query q = getSession().createQuery("from User where userID= :personID");
			q.setLong("personID", userId);		
			User user = (User) q.uniqueResult();
			user.setLocked(3);
			getSession().update(user);
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
			throw new UserException("Could not get user " + userId, e);
			
		}
	}
	public User unlockUser(Long userId) throws UserException{
		try {
			begin();
			Query q = getSession().createQuery("from User where userID= :personID");
			q.setLong("personID", userId);		
			User user = (User) q.uniqueResult();
			user.setLocked(0);
			getSession().update(user);
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
			throw new UserException("Could not get user " + userId, e);
			
		}
	}
	public List<User> selectAll() throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User");
			q.setComment("MyHQL:select all user");
				
			List<User> list = q.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user list ", e);
		}
	}
	
	public void update(User user) throws Exception {
		try {
			begin();
			getSession().update(user);
			commit();
		}catch(HibernateException e) {
			rollback();
			e.printStackTrace();
			throw new UserException("can not save the board");
		}
			
}
}
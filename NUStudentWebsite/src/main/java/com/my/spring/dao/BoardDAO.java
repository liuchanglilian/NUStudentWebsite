package com.my.spring.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.spring.exception.BoardException;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Board;
import com.my.spring.pojo.User;

public class BoardDAO extends DAO {

	public BoardDAO() {
	}

public List<Board> list() throws BoardException{
    	
    	try {
            begin();
            Query q = getSession().createQuery("from Board");
            List<Board> boards = q.list();
            commit();
            return boards;
        } catch (HibernateException e) {
            rollback();
            throw new BoardException("Could not do it ", e);
        }
    	
    }
	public Board get(String username, String password) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where userEmail = :username and userPassword = :password");
			q.setString("username", username);
			q.setString("password", password);			
			User user = (User) q.uniqueResult();
			commit();
			return null;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}
	
	public Board get(int boardId) throws BoardException {
		try {
			begin();
			Query q = getSession().createQuery("from Board where boardId= :boardId");
			q.setInteger("boardId", boardId);		
			Board board = (Board) q.uniqueResult();
			commit();
			return board;
		} catch (HibernateException e) {
			rollback();
			throw new BoardException("Could not get user " + boardId, e);
		}
	}
   
	public Board addBoard(Board b)
			throws BoardException {
		try {
			begin();
			System.out.println("inside boardDAO");

			getSession().save(b);
			commit();
			return b;

		} catch (HibernateException e) {
			rollback();
			throw new BoardException("Exception while addingBoard: " + e.getMessage());
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
	public void update(Board board) throws Exception {
		try {
			begin();
			getSession().update(board);
			commit();
		}catch(HibernateException e) {
			rollback();
			throw new BoardException("can not save the board");
		}
			
}
}

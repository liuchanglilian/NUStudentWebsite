package com.my.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.my.spring.exception.BoardException;
import com.my.spring.exception.PostException;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Board;
import com.my.spring.pojo.Post;
import com.my.spring.pojo.User;

public class PostDAO extends DAO{


	public PostDAO() {
	}
	public void save(Post post)
			throws PostException {
		try {
			begin();
			System.out.println("inside postDAO");

			getSession().save(post);
			commit();
			

		} catch (HibernateException e) {
			rollback();
			throw new PostException("Exception while addingBoard: " + e.getMessage());
		}
	}
	public Post getPost(long postId) {
		begin();
		Query q = getSession().createQuery("from Post where postId = :postId");
		q.setComment("MyHQL:from Post where postId = :postId");
		q.setLong("postId", postId);
		Post post=(Post) q.uniqueResult();			
		commit();
		return post;
	}
	public List<Post> getAllPost(long userId) throws PostException {
		try {
			begin();
			
			Criteria crit=getSession().createCriteria(Post.class);
			Criteria prdCrit=crit.createCriteria("user"); 
			prdCrit.add(Restrictions.eq("userId",userId));
			List<Post>results=crit.list();
			System.out.println("list length"+results.size());
			return results;
		} catch (HibernateException e) {
			rollback();
			throw new PostException("Could not get user " + userId, e);
		}
	}
	
	
	
	public List<Post> get(long boardId) throws PostException {
		try {
			begin();
			Query q = getSession().createQuery("from Post where fk_board= :board");
			q.setComment("MyHQL:from Post where fk_board= :board");
			q.setLong("board", boardId);		
			List<Post> list = q.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new PostException("Could not get user " + boardId, e);
		}
	}
	
	public void update(Post post) throws PostException {
		try {
			begin();
			getSession().update(post);
			commit();
		}catch(HibernateException e) {
			rollback();
			e.printStackTrace();
			throw new PostException("can not save the post");
		}
	}
	
}

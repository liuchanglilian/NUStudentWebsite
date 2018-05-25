package com.my.spring.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.spring.exception.MessageException;
import com.my.spring.exception.PostException;
import com.my.spring.pojo.Message;
import com.my.spring.pojo.Post;

public class MessageDAO extends DAO {
     
	public MessageDAO() {
		
	}
	public void save(Message message) throws MessageException{
	try {
		begin();
		System.out.println("inside MessageDAO");
        getSession().save(message);
		commit();
	} catch (HibernateException e) {
		rollback();
		e.printStackTrace();
		throw new MessageException("Exception while saving message: " );
	}
	}
	public Message getMessages(Long userId){
		try {
			begin();
			Query q = getSession().createQuery("from Message where fk_receiver= :receiver and hasRead is false order by date asc");
			q.setComment("MyHQL:from Post where fk_receiver= :receiver");
			q.setLong("receiver", userId);
			q.setMaxResults(1);
			Message message =(Message)q.uniqueResult();
			commit();
			return message;
		} catch (HibernateException e) {
			rollback();
		    return null;
		}
		
		
	}
	public void setRead(long maxId)  throws MessageException{
		try {
			begin();
			Query q = getSession().createQuery("from Message where messageId=:messageId");
			q.setLong("messageId", maxId);
			Message m=(Message)q.uniqueResult();
			m.setHasRead(true);
			 getSession().save(m);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
			throw new MessageException("Exception while saving message: " );
		}
		}
	
}

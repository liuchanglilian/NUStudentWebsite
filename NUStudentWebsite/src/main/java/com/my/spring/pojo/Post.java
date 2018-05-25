package com.my.spring.pojo;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;


@Entity
@Table(name="post_table")
public class Post implements Serializable{

   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private long postId;
   
   @ManyToOne
   @JoinColumn(name ="fk_user")
   private User user;
   
   @Column(name="title")
   private String title;
   
   @ManyToOne
   @JoinColumn(name ="fk_board")
   private Board board;
   
   @Column(name="text")
   private String text;
   
   @OneToMany(cascade=CascadeType.ALL,mappedBy="post")
    private Set<Reply> replies=new TreeSet<Reply>();
   @Column(name="replyNumber")
   private int replyNumber;
   
   
   private Date date;
   public void setDate(Date date) {
	this.date = date;
}
@Column(name="from")
   @Temporal(TemporalType.TIMESTAMP) 
   public Date getDate() {
       return date;
   }
public long getPostId() {
	return postId;
}

public void setPostId(long postId) {
	this.postId = postId;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Post() {
	
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public Set<Reply> getReplies() {
	return replies;
}

public void setReplies(Set<Reply> replies) {
	this.replies = replies;
}

public Board getBoard() {
	return board;
}

public void setBoard(Board board) {
	this.board = board;
}

public int getReplyNumber() {
	return replyNumber;
}

public void setReplyNumber(int replyNumber) {
	this.replyNumber = replyNumber;
}
}

package com.my.spring.pojo;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import com.smart.domain.User;

@Entity
@Table(name = "board_table")
public class Board implements Serializable {

	private long boardId;

	private String boardName;

	private int postNumber;

	public Board() {
		posts = new TreeSet<Post>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	@Column(name = "boardName")
	public String getBoardName() {
		return boardName;
	}

	private Set<Post> posts;

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	@Column(name = "postNumber")
	public int getPostNumber() {
		return postNumber;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "board")

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}

}

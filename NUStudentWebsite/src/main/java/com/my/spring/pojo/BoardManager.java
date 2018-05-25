package com.my.spring.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "BoardManager")
@PrimaryKeyJoinColumn(name = "ID")

public class BoardManager extends User implements Serializable {
	
	public BoardManager(String userEmail, String userPassword) {
		super(userEmail, userPassword);
		// TODO Auto-generated constructor stub
	}

	@OneToOne(cascade=CascadeType.ALL,optional=false)
	private Board board;

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}

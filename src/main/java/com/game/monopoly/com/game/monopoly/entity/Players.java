package com.game.monopoly.com.game.monopoly.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="players")
public class Players {

	@Id
	//@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "turns")
	private Integer turns;
	
	@Column(name = "current_place")
	private String currentPlace;
	
	@Column(name = "start_flag")
	private Boolean startFlag; 
	
	public Players() {}

	public Players(long id, String name, Integer amount, Integer turns, String currentPlace, Boolean startFlag) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.turns = turns;
		this.currentPlace = currentPlace;
		this.startFlag = startFlag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getTurns() {
		return turns;
	}

	public void setTurns(Integer turns) {
		this.turns = turns;
	}

	public String getCurrentPlace() {
		return currentPlace;
	}

	public void setCurrentPlace(String currentPlace) {
		this.currentPlace = currentPlace;
	}

	public Boolean getStartFlag() {
		return startFlag;
	}

	public void setStartFlag(Boolean startFlag) {
		this.startFlag = startFlag;
	}
	
}

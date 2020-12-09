package com.game.monopoly.com.game.monopoly.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="places")
public class Places {
	
	@Id
	//@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "place")
	private String place;
	
	@Column(name = "buy_rate")
	private Integer buyRate;
	
	@Column(name = "rent_rate")
	private Integer rentRate;
	
	@OneToOne
	@JoinColumn(name = "players_id")
	private Players boughtByPlayer;
	
	public Places() {}
	
	public Places(long id, String place, Integer buyRate, Integer rentRate, Players boughtByPlayer) {
		super();
		this.id = id;
		this.place = place;
		this.buyRate = buyRate;
		this.rentRate = rentRate;
		this.boughtByPlayer = boughtByPlayer;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setBuyRate(Integer Rate) {
	this.buyRate = Rate;
	}
	public Integer getBuyRate() {
	return buyRate;
	}
	public void setRentRate(Integer Rate) {
	this.rentRate = Rate;
	}
	public Integer getRentRate() {
	return rentRate;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Players getBoughtByPlayer() {
		return boughtByPlayer;
	}
	public void setBoughtByPlayer(Players boughtByPlayer) {
		this.boughtByPlayer = boughtByPlayer;
	}

}

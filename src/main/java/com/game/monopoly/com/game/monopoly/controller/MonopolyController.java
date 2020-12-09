package com.game.monopoly.com.game.monopoly.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.game.monopoly.com.game.monopoly.entity.Places;
import com.game.monopoly.com.game.monopoly.entity.Players;
import com.game.monopoly.com.game.monopoly.repository.PlacesRepository;
import com.game.monopoly.com.game.monopoly.repository.PlayersRepository;

@RestController
public class MonopolyController {
	
	@Autowired
	PlacesRepository placesRepository;
	
	@Autowired
	PlayersRepository playersRepository;
	
	public static final String start_place = "Pall Mall";

	@GetMapping("/create-game")
	public String createMonopolyGame() {
		resetTableInfo();
		return "Game Created Successfully";
	}
	
	@GetMapping("/roll-die/{playerId}")
	public String onDieRoll(@PathVariable(value = "playerId") Long player) {
		if(player != 1 && player != 2)
			return "Only Player 1 and Player 2 are allowed to play.";
		
		int dieRollValue = getRandom();
		long place = dieRollValue;
		if(place>10)
			place = (long) (dieRollValue%10);
		
		
		Places placeDetail = placesRepository.findById(place).get();
		Players playerDetail = playersRepository.findById(player).get();
		Integer balance = 0;
		String message = null;
		
		//cases
		
		if(placeDetail.getBoughtByPlayer()==null) {
			//update Places entity with player bought the particular place.
			placeDetail.setBoughtByPlayer(playerDetail);
			placesRepository.save(placeDetail);
			
			
			balance = playerDetail.getAmount()-placeDetail.getBuyRate();
			message = "Player " + playerDetail.getId() + ": Die rolled "+ dieRollValue +" and landed on Place " + placeDetail.getPlace() + 
					", unclaimed place and hence bought for $" + placeDetail.getBuyRate() + ". Remaining balance is $"+ balance +".";
		} 
		else if((long)placeDetail.getBoughtByPlayer().getId() != player) {
			balance = playerDetail.getAmount()-placeDetail.getRentRate();
			message = "Player " + playerDetail.getId() + ": Die rolled "+ dieRollValue +" and landed on Place " + placeDetail.getPlace() + 
			", paid rent $" + placeDetail.getRentRate() + ". Remaining balance is $"+ balance +".";
			
			
			//rent amount paid is added to another player's account
			Players secondplayer = playersRepository.findById((long)placeDetail.getBoughtByPlayer().getId()).get();
			int updatedAm = secondplayer.getAmount() + placeDetail.getRentRate();
			secondplayer.setAmount(updatedAm);
			playersRepository.save(secondplayer);
			message = message + " Player " +secondplayer.getId() + " updated balance is $" + updatedAm;
		}
		else {
			balance = playerDetail.getAmount();
			message = "Player " + playerDetail.getId() + ": Die rolled "+ dieRollValue +" and landed on Place " + placeDetail.getPlace() + 
					", place is already owned. Balance is $"+ balance +".";
		}
		
		
		//Add credit if Start Place is crossed first time by a player.
		if(playerDetail.getCurrentPlace() == start_place && playerDetail.getStartFlag()==false) {
			balance = balance+200;
			message = message + " Also Crossed “Start” gaining $200. Remaining Balance $" + balance +".";
			playerDetail.setStartFlag(true);
		}
		
		Integer turnCount = playerDetail.getTurns();
		turnCount++;
		
		//update Player Information
		playerDetail.setAmount(balance);
		playerDetail.setCurrentPlace(placeDetail.getPlace());
		playerDetail.setTurns(turnCount);
		playersRepository.save(playerDetail);
		
		//when player is bankrupted
		if(balance<0) {
			message = message + " Game Over, You lose!";
			resetTableInfo();
			return message + " New game created!!";
		}
		
		//Announce Result when total of 50 turns are completed.
		Players p1 = playersRepository.findById((long)1).get();
		Players p2 = playersRepository.findById((long)2).get();
		
		if(p1.getTurns()+p2.getTurns() == 50) {
			Integer highestCashPlayer = 1;
			if(p2.getAmount()>p1.getAmount())
				highestCashPlayer = 2;
			
			if(playerDetail.getId() == highestCashPlayer) {
				message = message + " Congrats, You won!";
			} else {
				message = message + " Game Over, You lose!";
			}			
			resetTableInfo();
			message = message + " New game created!!";
		}
		
		return message;
	}
	
	void resetTableInfo() {
		placesRepository.deleteAll();
		playersRepository.deleteAll();
		
		Players p1 = new Players(1, "p1", 1000, 0, null, false);
		Players p2 = new Players(2, "p2", 1000, 0, null, false);
		playersRepository.save(p1);
		playersRepository.save(p2);
		
		Places a1 = new Places(1, "Old Kent Road", 60, 30, null);
		Places a2 = new Places(2, "Whitechapel Road", 60, 30, null);
		Places a3 = new Places(3, "King's Cross station", 200, 100, null);
		Places a4 = new Places(4, "The Angel Islington", 100, 50, null);
		Places a5 = new Places(5, "Euston Road", 100, 50, null);
		Places a6 = new Places(6, "Pentonville Road", 120, 60, null);
		Places a7 = new Places(7, "Pall Mall", 140, 70, null);
		Places a8 = new Places(8, "Whitehall", 140, 70, null);
		Places a9 = new Places(9, "Northumberland Avenue", 160, 80, null);
		Places a10 = new Places(10, "Marylebone station", 200, 100, null);
		List<Places> placeslist = Arrays.asList(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10);
		placesRepository.saveAll(placeslist);
		
		//List<Places> check1 = placesRepository.findAll();
		//List<Players> check2 = playersRepository.findAll();
	}
	
	//get random value in range 2-12 for 2 dice rolled.
	int getRandom() {
		Random random = new Random();
		int rand = 0;
		while (true){
		    rand = random.nextInt(13);
		    if(rand !=0 && rand!=1) 
		    	break;
		}
		return rand;
	}
	
}

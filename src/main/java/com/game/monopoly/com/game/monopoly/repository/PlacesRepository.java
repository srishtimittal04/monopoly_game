package com.game.monopoly.com.game.monopoly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.monopoly.com.game.monopoly.entity.Places;

@Repository
public interface PlacesRepository extends JpaRepository<Places, Long>{}

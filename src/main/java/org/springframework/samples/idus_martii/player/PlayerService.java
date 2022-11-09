package org.springframework.samples.idus_martii.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;

@Service
public class PlayerService {
	private PlayerRepository playerRepo;
	
	@Autowired
	public PlayerService(PlayerRepository playerRepo) {
		this.playerRepo = playerRepo;
	}
	
	public List<Player> getAll(){
		return this.playerRepo.findAllPlayers();
	}
}

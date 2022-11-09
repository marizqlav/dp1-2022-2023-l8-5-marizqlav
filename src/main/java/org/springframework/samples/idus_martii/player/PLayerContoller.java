package org.springframework.samples.idus_martii.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PLayerContoller {
	
	private final PlayerService playerService;
	@Autowired
	public PLayerContoller(PlayerService playerService) {
		this.playerService = playerService;
	}

}

package org.brutality.model.content;

import org.brutality.model.players.Player;

public class PlayerContent {

	private final Player player;
	
	public PlayerContent(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}

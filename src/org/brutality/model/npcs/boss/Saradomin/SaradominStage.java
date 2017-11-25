package org.brutality.model.npcs.boss.Saradomin;

import org.brutality.event.CycleEvent;
import org.brutality.model.players.Player;

public abstract class SaradominStage extends CycleEvent {
	
	protected Saradomin saradomin;
	
	protected Player player;
	
	public SaradominStage(Saradomin saradomin, Player player) {
		this.saradomin = saradomin;
		this.player = player;
	}

}

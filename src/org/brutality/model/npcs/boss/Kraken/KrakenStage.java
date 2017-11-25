package org.brutality.model.npcs.boss.Kraken;

import org.brutality.event.CycleEvent;
import org.brutality.model.players.Player;

public abstract class KrakenStage extends CycleEvent {
	
	protected Kraken kraken;
	
	protected Player player;
	
	public KrakenStage(Kraken kraken, Player player) {
		this.kraken = kraken;
		this.player = player;
	}

}

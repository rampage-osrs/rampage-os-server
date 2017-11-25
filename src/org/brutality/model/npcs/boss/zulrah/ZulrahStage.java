package org.brutality.model.npcs.boss.zulrah;

import org.brutality.event.CycleEvent;
import org.brutality.model.players.Player;

public abstract class ZulrahStage extends CycleEvent {
	
	protected Zulrah zulrah;
	
	protected Player player;
	
	public ZulrahStage(Zulrah zulrah, Player player) {
		this.zulrah = zulrah;
		this.player = player;
	}

}

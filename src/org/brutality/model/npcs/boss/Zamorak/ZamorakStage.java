package org.brutality.model.npcs.boss.Zamorak;

import org.brutality.event.CycleEvent;
import org.brutality.model.players.Player;

public abstract class ZamorakStage extends CycleEvent {
	
	protected Zamorak zamorak;
	
	protected Player player;
	
	public ZamorakStage(Zamorak zamorak, Player player) {
		this.zamorak = zamorak;
		this.player = player;
	}

}

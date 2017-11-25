package org.brutality.model.npcs.boss.Armadyl;

import org.brutality.event.CycleEvent;
import org.brutality.model.players.Player;

public abstract class ArmadylStage extends CycleEvent {
	
	protected Armadyl armadyl;
	
	protected Player player;
	
	public ArmadylStage(Armadyl armadyl, Player player) {
		this.armadyl = armadyl;
		this.player = player;
	}

}

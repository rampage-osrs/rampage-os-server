package org.brutality.model.npcs.boss.Bandos;

import org.brutality.event.CycleEvent;
import org.brutality.model.players.Player;

public abstract class BandosStage extends CycleEvent {

	protected Bandos bandos;

	protected Player player;

	public BandosStage(Bandos bandos, Player player) {
		this.bandos = bandos;
		this.player = player;
	}
}

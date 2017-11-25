package org.brutality.model.npcs.boss.Kalphite;

import org.brutality.event.CycleEvent;
import org.brutality.model.npcs.boss.Kalphite.Kalphite;
import org.brutality.model.players.Player;

public abstract class Stage extends CycleEvent {

	protected Kalphite kalphite;

	protected Player player;

	public Stage(Kalphite kalphite, Player player) {
		this.kalphite = kalphite;
		this.player = player;
	}
}

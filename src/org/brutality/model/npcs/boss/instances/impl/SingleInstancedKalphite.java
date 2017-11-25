package org.brutality.model.npcs.boss.instances.impl;

import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.npcs.boss.Kalphite.Kalphite;
import org.brutality.model.npcs.boss.instances.SingleInstancedArea;
import org.brutality.model.players.Boundary;
import org.brutality.model.players.Player;

public class SingleInstancedKalphite extends SingleInstancedArea {
	
	public SingleInstancedKalphite(Player player, Boundary boundary, int height) {
		super(player, boundary, height);
	}
	
	@Override
	public void onDispose() {
		Kalphite kalphite = player.getKalphite();
		if (player.getKalphite().getNpc() != null) {
			NPCHandler.kill(player.getKalphite().getNpc().npcType, height);
		}
	}
}

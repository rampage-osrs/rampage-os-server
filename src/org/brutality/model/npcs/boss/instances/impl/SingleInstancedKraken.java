package org.brutality.model.npcs.boss.instances.impl;

import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.npcs.boss.Kraken.Kraken;
import org.brutality.model.npcs.boss.instances.SingleInstancedArea;
import org.brutality.model.players.Boundary;
import org.brutality.model.players.Player;

public class SingleInstancedKraken extends SingleInstancedArea {
	
	public SingleInstancedKraken(Player player, Boundary boundary, int height) {
		super(player, boundary, height);
	}
	
	@Override
	public void onDispose() {
		Kraken kraken = player.getKraken();
		if (player.getKraken().getNpc() != null) {
			NPCHandler.kill(player.getKraken().getNpc().npcType, height);
		}
		//Server.getGlobalObjects().remove(17000, height);
		//NPCHandler.kill(Zulrah.SNAKELING, height);
	}

}

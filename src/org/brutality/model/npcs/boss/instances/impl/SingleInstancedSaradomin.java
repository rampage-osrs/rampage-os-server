package org.brutality.model.npcs.boss.instances.impl;

import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.npcs.boss.Saradomin.Saradomin;
import org.brutality.model.npcs.boss.instances.SingleInstancedArea;
import org.brutality.model.players.Boundary;
import org.brutality.model.players.Player;

public class SingleInstancedSaradomin extends SingleInstancedArea {
	
	public SingleInstancedSaradomin(Player player, Boundary boundary, int height) {
		super(player, boundary, height);
	}
	
	@Override
	public void onDispose() {
		Saradomin saradomin = player.getSaradomin();
		if (player.getSaradomin().getNpc() != null) {
			NPCHandler.kill(player.getSaradomin().getNpc().npcType, height);
		}
		//Server.getGlobalObjects().remove(17000, height);
		//NPCHandler.kill(Zulrah.SNAKELING, height);
	}

}

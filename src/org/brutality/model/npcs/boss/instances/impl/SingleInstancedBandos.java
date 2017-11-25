package org.brutality.model.npcs.boss.instances.impl;

import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.npcs.boss.Bandos.Bandos;
import org.brutality.model.npcs.boss.instances.SingleInstancedArea;
import org.brutality.model.players.Boundary;
import org.brutality.model.players.Player;

public class SingleInstancedBandos extends SingleInstancedArea {
	
	public SingleInstancedBandos(Player player, Boundary boundary, int height) {
		super(player, boundary, height);
	}
	
	@Override
	public void onDispose() {
		Bandos bandos = player.getBandos();
		if (player.getBandos().getNpc() != null) {
			NPCHandler.kill(player.getBandos().getNpc().npcType, height);
		}
		//Server.getGlobalObjects().remove(17000, height);
		//NPCHandler.kill(Zulrah.SNAKELING, height);
	}

}

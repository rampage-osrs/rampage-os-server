package org.brutality.model.npcs.boss.instances.impl;

import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.npcs.boss.Armadyl.Armadyl;
import org.brutality.model.npcs.boss.instances.SingleInstancedArea;
import org.brutality.model.players.Boundary;
import org.brutality.model.players.Player;

public class SingleInstancedArmadyl extends SingleInstancedArea {
	
	public SingleInstancedArmadyl(Player player, Boundary boundary, int height) {
		super(player, boundary, height);
	}
	
	@Override
	public void onDispose() {
		Armadyl armadyl = player.getArmadyl();
		if (player.getArmadyl().getNpc() != null) {
			NPCHandler.kill(player.getArmadyl().getNpc().npcType, height);
		}
		//Server.getGlobalObjects().remove(17000, height);
		//NPCHandler.kill(Zulrah.SNAKELING, height);
	}

}

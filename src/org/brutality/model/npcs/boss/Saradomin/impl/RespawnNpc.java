package org.brutality.model.npcs.boss.Saradomin.impl;

import org.brutality.Server;
import org.brutality.event.CycleEventContainer;
import org.brutality.model.npcs.boss.Saradomin.Saradomin;
import org.brutality.model.npcs.boss.Saradomin.SaradominStage;
import org.brutality.model.players.Player;


public class RespawnNpc extends SaradominStage {

	public RespawnNpc(Saradomin saradomin, Player player) {
		super(saradomin, player);
	}

	@Override
	public void execute(CycleEventContainer container) {
		try {
			if (container.getOwner() == null || saradomin == null || player == null || player.isDead
					|| saradomin.getInstancedSaradomin() == null) {
				container.stop();
				return;
			}
			int cycle = container.getTotalTicks();
			if (cycle == 13) {
				Server.npcHandler.spawnNpc(player, 2205, 2898, 5266, 8, 1, 255, 20, 350, 250, false, false);
				Server.npcHandler.spawnNpc(player, 2206, 2901, 5271, 8, 1, 100, 20, 80, 60, false, false);
				Server.npcHandler.spawnNpc(player, 2207, 2903, 5261, 8, 1, 100, 20, 80, 60, false, false);
				Server.npcHandler.spawnNpc(player, 2208, 2893, 5267, 8, 1, 100, 20, 80, 60, false, false);
				player.SARADOMIN_CLICKS = 0;
				container.stop();
			}
			stop();
		} catch (Exception e) {

		}
	}
}

package org.brutality.model.npcs.boss.Bandos.impl;

import org.brutality.Server;
import org.brutality.event.CycleEventContainer;
import org.brutality.model.npcs.boss.Bandos.Bandos;
import org.brutality.model.npcs.boss.Bandos.BandosStage;
import org.brutality.model.players.Player;


public class RespawnNpc extends BandosStage {

	public RespawnNpc(Bandos bandos, Player player) {
		super(bandos, player);
	}

	@Override
	public void execute(CycleEventContainer container) {
		try {
			if (container.getOwner() == null || bandos == null || player == null || player.isDead
					|| bandos.getInstancedBandos() == null) {
				container.stop();
				return;
			}
			int cycle = container.getTotalTicks();
			if (cycle == 13) {
				Server.npcHandler.spawnNpc(player, 2215, 2870, 5359, bandos.getInstancedBandos().getHeight() + 6, 1, 255, 20, 350, 250, false, false);
				Server.npcHandler.spawnNpc(player, 2216, 2867, 5362, bandos.getInstancedBandos().getHeight() + 6, 1, 100, 20, 80, 60, false, false);
				Server.npcHandler.spawnNpc(player, 2217, 2871, 5354, bandos.getInstancedBandos().getHeight() + 6, 1, 100, 20, 80, 60, false, false);
				Server.npcHandler.spawnNpc(player, 2218, 2874, 5360, bandos.getInstancedBandos().getHeight() + 6, 1, 100, 20, 80, 60, false, false);
				player.BANDOS_CLICKS = 0;
				container.stop();
			}
			stop();
		} catch (Exception e) {

		}
	}
}

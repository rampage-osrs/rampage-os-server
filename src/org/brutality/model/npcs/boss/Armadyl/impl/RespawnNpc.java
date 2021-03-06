package org.brutality.model.npcs.boss.Armadyl.impl;

import org.brutality.Server;
import org.brutality.event.CycleEventContainer;
import org.brutality.model.npcs.boss.Armadyl.Armadyl;
import org.brutality.model.npcs.boss.Armadyl.ArmadylStage;
import org.brutality.model.players.Player;


public class RespawnNpc extends ArmadylStage {

	public RespawnNpc(Armadyl armadyl, Player player) {
		super(armadyl, player);
	}

	@Override
	public void execute(CycleEventContainer container) {
		try {
			if (container.getOwner() == null || armadyl == null || player == null || player.isDead
					|| armadyl.getInstancedArmadyl() == null) {
				container.stop();
				return;
			}
			int cycle = container.getTotalTicks();
			if (cycle == 13) {
				Server.npcHandler.spawnNpc(player, 3162, 2833, 5303, armadyl.getInstancedArmadyl().getHeight() + 6, 1, 255, 23, 200, 300, false, false);
				Server.npcHandler.spawnNpc(player, 3163, 2830, 5304, armadyl.getInstancedArmadyl().getHeight() + 6, 1, 100, 13, 80, 60, false, false);
				Server.npcHandler.spawnNpc(player, 3164, 2830, 5300, armadyl.getInstancedArmadyl().getHeight() + 6, 1, 100, 13, 80, 60, false, false);
				Server.npcHandler.spawnNpc(player, 3165, 2839, 5301, armadyl.getInstancedArmadyl().getHeight() + 6, 1, 100, 13, 80, 60, false, false);
				player.ARMADYL_CLICKS = 0;
				container.stop();
			}
			stop();
		} catch (Exception e) {

		}
	}
}

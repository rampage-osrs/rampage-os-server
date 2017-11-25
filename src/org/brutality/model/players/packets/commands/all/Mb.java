package org.brutality.model.players.packets.commands.all;

import org.brutality.Config;
import org.brutality.Server;
import org.brutality.model.content.teleport.Position;
import org.brutality.model.content.teleport.TeleportExecutor;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Teleport the player to the mage bank.
 * 
 * @author Emiel
 */
public class Mb implements Command {

	@Override
	public void execute(Player c, String input) {
		if (!Config.PLACEHOLDER_ECONOMY) {
			if (Server.getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.inWild() || c.inCamWild()) {
				return;
			}
			TeleportExecutor.teleport(c, new Position(2539, 4716, 0));
		}
	}
}

package org.brutality.model.players.packets.commands.all;

import org.brutality.Config;
import org.brutality.Server;
import org.brutality.model.content.teleport.Position;
import org.brutality.model.content.teleport.TeleportExecutor;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Teleport the player to easts.
 * 
 * @author Emiel
 */
public class Easts implements Command {

	@Override
	public void execute(Player c, String input) {
		if (!Config.PLACEHOLDER_ECONOMY) {
			if (Server.getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.inWild() || c.inCamWild()) {
				return;
			}
			TeleportExecutor.teleport(c, new Position(3353, 3684, 0));
		}
	}
}

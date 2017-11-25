package org.brutality.model.players.packets.commands.all;

import org.brutality.Server;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Teleport the player to the Make-over Mage.
 * 
 * @author Emiel
 */
public class Char implements Command {

	@Override
	public void execute(Player c, String input) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		if (c.inWild() || c.inCamWild()) {
			return;
		}
		c.getPA().showInterface(3559);
	}
}

package org.brutality.model.players.packets.commands.donator;

import org.brutality.model.content.teleport.Position;
import org.brutality.model.content.teleport.TeleportExecutor;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Teleports the player to the donator zone.
 * 
 * @author Emiel
 */
public class Donatorzone implements Command {

	@Override
	public void execute(Player c, String input) {
		if (c.inTrade || c.inDuel || c.inWild()) {
			return;
		}
		TeleportExecutor.teleport(c, new Position(3482, 3237, 0));
	}
}

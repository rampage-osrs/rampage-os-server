package org.brutality.model.players.packets.commands.developer;

import org.brutality.model.content.teleport.Position;
import org.brutality.model.content.teleport.TeleportExecutor;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Teleport the player to the staffzone.
 * 
 * @author Emiel
 */
public class Staffzone implements Command {

	@Override
	public void execute(Player c, String input) {
		TeleportExecutor.teleport(c, new Position(2912, 5475, 0));
	}
}

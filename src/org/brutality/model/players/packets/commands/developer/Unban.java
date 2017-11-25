package org.brutality.model.players.packets.commands.developer;

import org.brutality.Connection;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Unbans a given player.
 * 
 * @author Emiel
 */
public class Unban implements Command {

	@Override
	public void execute(Player c, String input) {
		Connection.removeNameFromBanList(input);
		c.sendMessage(input + " has been unbanned.");
	}
}

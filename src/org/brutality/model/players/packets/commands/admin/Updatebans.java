package org.brutality.model.players.packets.commands.admin;

import org.brutality.Connection;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Updates the list of all banned accounts.
 * 
 * @author Emiel
 */
public class Updatebans implements Command {

	@Override
	public void execute(Player c, String input) {
		Connection.resetIpBans();
	}
}

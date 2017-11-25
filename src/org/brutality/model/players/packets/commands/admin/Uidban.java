package org.brutality.model.players.packets.commands.admin;

import org.brutality.Connection;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.packets.commands.Command;

public class Uidban implements Command {

	@Override
	public void execute(Player c, String input) {
		Player target = PlayerHandler.getPlayer(input);
		
		if (target == null) {
			c.sendMessage("The player " + input + " is offline.");
			return;
		}
		Connection.addBannedUid(target.playerName.toLowerCase(), target.getUniqueIdentifier());
		target.disconnected = true;
		c.sendMessage("Successfully uid banned " + input + ".");
	}

}

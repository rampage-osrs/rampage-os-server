package org.brutality.model.players.packets.commands.moderator;


import java.util.Optional;

import org.brutality.Server;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.players.ConnectedFrom;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.packets.commands.Command;

/**
 * Forces a given player to log out.
 * 
 * @author Emiel
 */
public class Forcekick implements Command {

	@Override
	public void execute(Player c, String input) {
		Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(input);
		
		if (optionalPlayer.isPresent()) {
			Player c2 = optionalPlayer.get();
			if (Server.getMultiplayerSessionListener().inAnySession(c)) {
				c.sendMessage("The player is in a trade, or duel. You cannot do this at this time.");
				return;
			}
			c2.disconnected = true;
			c.sendMessage("Kicked " + c2.playerName);
		} else {
			c.sendMessage(input + " is not online. You can only kick online players.");
		}
	}
}

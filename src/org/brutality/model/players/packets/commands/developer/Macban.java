package org.brutality.model.players.packets.commands.developer;

import java.util.Optional;

import org.brutality.Connection;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.packets.commands.Command;

/*
 * Author - Mikey96
 */

public class Macban implements Command {
	
	@Override
	public void execute(Player c, String input) {
		Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(input);
			if (optionalPlayer.isPresent()) {
				Player c2 = optionalPlayer.get();
				Connection.addMacBan(c2.getMacAddress());
				c2.properLogout = true;
				c2.disconnected = true;
				CycleEventHandler.getSingleton().stopEvents(c2);
				c.sendMessage("@red@You have MAC Banned: " + c2.playerName);
				return;
			} else if (input.length() < 1) {
				c.sendMessage("Please enter a valid username.");
			} else 
				c.sendMessage(input + " doesn't seem to be online.");
	}

}

package org.brutality.model.players.packets.commands.donator;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Changes the title of the player to their default donator title.
 * 
 * @author Emiel
 */
public class Donatortitle implements Command {

	@Override
	public void execute(Player c, String input) {
		c.sendMessage("You will now get your donator title instead. Relog for changes to take effect.");
		c.keepTitle = false;
		c.killTitle = false;
	}
}

package org.brutality.model.players.packets.commands.owner;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Toggle Bounty Hunter on or off.
 * 
 * @author Emiel
 *
 */
public class Bh implements Command {

	@Override
	public void execute(Player c, String input) {
		//Config.BOUNTY_HUNTER_ACTIVE = Config.BOUNTY_HUNTER_ACTIVE ? false : true;
		//c.sendMessage(Config.BOUNTY_HUNTER_ACTIVE ? "Bounty hunter is now active." : "Bounty hunter is no longer active.");
	}
}

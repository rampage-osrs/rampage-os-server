package org.brutality.model.players.packets.commands.developer;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Show a red skull above the player's head.
 * 
 * @author Emiel
 *
 */
public class Visible implements Command {

	@Override
	public void execute(Player c, String input) {
		c.isNpc = false;
		c.updateRequired = true;
		c.appearanceUpdateRequired = true;
		c.sendMessage("Rogue mode disabled!");
	}
}

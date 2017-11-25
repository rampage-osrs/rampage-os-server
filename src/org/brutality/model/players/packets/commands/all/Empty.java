package org.brutality.model.players.packets.commands.all;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Empty the inventory of the player.
 * 
 * @author Emiel
 */
public class Empty implements Command {

	@Override
	public void execute(Player c, String input) {
		if (!c.inWild() && !c.inCamWild() && !c.inDuel) {
			c.getPA().removeAllItems();
			c.sendMessage("You empty your inventory.");
		} else {
			c.sendMessage("You cannot empty your inventory here.");
		}
	}
}
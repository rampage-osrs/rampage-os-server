package org.brutality.model.players.packets.commands.developer;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Open the banking interface.
 * 
 * @author Emiel
 */
public class Bank implements Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().openUpBank();
	}
}

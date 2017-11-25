package org.brutality.model.players.packets.commands.owner;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Open a specific interface.
 * 
 * @author Emiel
 *
 */
public class Gfx implements Command {

	@Override
	public void execute(Player c, String input) {
		c.gfx0(Integer.parseInt(input));
	}
}

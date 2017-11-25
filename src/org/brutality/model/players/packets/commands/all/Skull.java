package org.brutality.model.players.packets.commands.all;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Tell the player where they can get a skull.
 * 
 * @author Emiel
 *
 */
public class Skull implements Command {

	@Override
	public void execute(Player c, String input) {
		c.sendMessage("<col=FF0000>Talk to the Emblem Trader in edgeville to receive a skull, or extended skull.</col>");
	}
}

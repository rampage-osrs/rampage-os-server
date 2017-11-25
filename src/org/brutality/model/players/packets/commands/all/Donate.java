package org.brutality.model.players.packets.commands.all;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Opens the store page in the default web browser.
 * 
 * @author Emiel
 */
public class Donate implements Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendFrame126("www.store.osbrutality.com", 12000);
	}
}

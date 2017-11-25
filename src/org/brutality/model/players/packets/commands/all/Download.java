package org.brutality.model.players.packets.commands.all;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Opens the download page in the default web browser.
 * 
 * @author Emiel
 */
public class Download implements Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendFrame126("www.osbrutality.com/OSBrutality.jar", 12000);
	}
}

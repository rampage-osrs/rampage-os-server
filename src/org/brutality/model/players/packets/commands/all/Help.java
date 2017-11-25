package org.brutality.model.players.packets.commands.all;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Opens the help interface.
 * 
 * @author Emiel
 */
public class Help implements Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().closeAllWindows();
		c.getPA().showInterface(59525);
	}
}

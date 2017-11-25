package org.brutality.model.players.packets.commands.owner;

import org.brutality.model.content.help.HelpDatabase;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Opens an interface containing all help tickets.
 * 
 * @author Emiel
 */
public class Helpdb implements Command {

	@Override
	public void execute(Player c, String input) {
		HelpDatabase.getDatabase().openDatabase(c);
	}
}

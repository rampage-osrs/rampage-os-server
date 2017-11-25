package org.brutality.model.players.packets.commands.developer;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

public class Empty implements Command {

	@Override
	public void execute(Player c, String input) {
		c.getItems().deleteAllItems();
	}

}

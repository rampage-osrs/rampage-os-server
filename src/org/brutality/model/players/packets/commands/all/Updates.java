package org.brutality.model.players.packets.commands.all;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

public class Updates implements Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendFrame126("www.forums.osbrutality.com/index.php?/forum/3-updates/", 12000);
	}

}

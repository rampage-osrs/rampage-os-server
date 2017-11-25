package org.brutality.model.players.packets.commands.all;

import org.brutality.model.content.teleport.Position;
import org.brutality.model.content.teleport.TeleportExecutor;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

public class Home implements Command {

	@Override
	public void execute(Player c, String input) {
		TeleportExecutor.teleport(c, new Position(3087, 3500, 0));
	}
}

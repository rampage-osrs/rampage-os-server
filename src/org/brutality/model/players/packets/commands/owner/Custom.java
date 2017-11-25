package org.brutality.model.players.packets.commands.owner;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Random Object is random.
 * 
 * @author Emiel
 *
 */
public class Custom implements Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().checkObjectSpawn(411, 2340, 9806, 2, 10);
	}
}

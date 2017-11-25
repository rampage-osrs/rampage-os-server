package org.brutality.model.players.packets.commands.developer;

import org.brutality.model.minigames.hunger.HungerManager;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

public class Endhunger implements Command {

	@Override
	public void execute(Player c, String input) {
		HungerManager.getSingleton().endGame(true);
	}


}

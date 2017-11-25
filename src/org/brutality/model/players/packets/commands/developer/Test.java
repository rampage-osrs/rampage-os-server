package org.brutality.model.players.packets.commands.developer;

import org.brutality.model.content.dialogue.teleport.TeleportDialogue;
import org.brutality.model.content.dialogue.teleport.Teleports;
import org.brutality.model.npcs.boss.abyssalsire.AbyssalSire;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

public class Test implements Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().openItemsKeptOnDeath();
	}
}

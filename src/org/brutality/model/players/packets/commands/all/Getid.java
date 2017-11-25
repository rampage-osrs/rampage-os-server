package org.brutality.model.players.packets.commands.all;

import org.brutality.model.items.ItemDefinition;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

public class Getid implements Command {

	@Override
	public void execute(Player c, String input) {
		if (input.length() < 3) {
			c.sendMessage("You must give at least 3 letters of input to narrow down the item.");
			return;
		}
		int results = 0;
		c.sendMessage("Searching: " + input);
		for (ItemDefinition it : ItemDefinition.DEFINITIONS) {
			if (results == 100) {
				c.sendMessage("Too many results! Please refine your search.");
				return;
			}
			if (it == null || it.getName() == null || it.getName().replaceAll(" ", "").equals(""))
				continue;
			if (!it.isNoted())
				if (it.getName().replace("_", " ").toLowerCase().contains(input.toLowerCase())) {
					c.sendMessage("<col=FF0000>" + it.getName().replace("_", " ") + " - " + it.getId());
					results++;
				}
		}
		c.sendMessage(results + " results found...");
	}
}

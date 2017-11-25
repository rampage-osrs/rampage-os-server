package org.brutality.model.players.packets.commands.owner;

import org.brutality.model.items.ItemDefinition;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

public class Itemn implements Command {

	@Override
	public void execute(Player c, String input) {
		String args[] = input.split(" ");
		String item = args[0].replaceAll("_", " ");
		c.sendMessage("Searching for: " + item);
		for (ItemDefinition it : ItemDefinition.DEFINITIONS) {
			if (it != null){
				if (it.getName().equals(item)) {
					c.sendMessage("Found: " + item + " " + it.getId());
				}
			}
		}
	}

}

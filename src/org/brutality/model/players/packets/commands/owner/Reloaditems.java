package org.brutality.model.players.packets.commands.owner;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;
import org.brutality.util.json.ItemDefinitionLoader;

/**
 * Reload the item and price configs.
 * 
 * @author Emiel
 *
 */
public class Reloaditems implements Command {

	@Override
	public void execute(Player c, String input) {
		// should really be done asynchronously...
		new ItemDefinitionLoader().load();
		
		c.sendMessage("@don2@[Load] Reloading @blu@item.cfg@bla@ and @blu@prices.txt");
	}
}

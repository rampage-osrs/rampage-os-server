package org.brutality.model.players.packets.commands.owner;

import org.brutality.Server;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Reload all shops.
 * 
 * @author Emiel
 *
 */
public class Reloadshops implements Command {

	@Override
	public void execute(Player c, String input) {
		Server.shopHandler = new org.brutality.world.ShopHandler();
		c.sendMessage("[Load] Reloading @blu@Shop Config.cfg");
	}
}

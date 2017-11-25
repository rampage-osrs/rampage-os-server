package org.brutality.model.players.packets.commands.all;

import org.brutality.Server;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Shows a list of commands.
 * 
 * @author Emiel
 *
 */
public class Commands implements Command {

	@Override
	public void execute(Player c, String input) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		for (int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
		c.getPA().sendFrame126("@dre@ServerName Spawn Commands", 8144);
		c.getPA().sendFrame126("", 8145);
		c.getPA().sendFrame126("@blu@::players@bla@ - Shows players online", 8147);
		c.getPA().sendFrame126("@blu@::vote@bla@ - Takes you to the voting page", 8148);
		c.getPA().sendFrame126("@blu@::auth@bla@ - Claim your voting reward", 8149);
		c.getPA().sendFrame126("@blu@::getitem itemname@bla@ - Auth the ID of an item", 8150);
		c.getPA().sendFrame126("@blu@::empty@bla@ - Destroy all items in your inventory", 8151);
		c.getPA().sendFrame126("@blu@::forums@bla@ - Takes you to the forums", 8152);
		c.getPA().sendFrame126("@blu@::donate@bla@ - Takes you to the donation page", 8153);
		c.getPA().sendFrame126("@blu@::changepassword newpass@bla@ - Changes your password", 8154);
		c.getPA().sendFrame126("@blu@::lock@bla@ - Locks/Unlocks your XP", 8155);
		c.getPA().sendFrame126("@blu@::char@bla@ - Teleports you to the Make-over Mage", 8156);
		c.getPA().sendFrame126("@blu@::highscores@bla@ - Brings you to the highscores", 8157);
		c.getPA().sendFrame126("@blu@::mb/::wests/::gdz/::easts@bla@ - Teles you to these hotspots", 8158);
		//c.getPA().sendFrame126("@blu@::hotkeys@bla@ - Toggles your hotkeys", 8163);
		//c.getPA().sendFrame126("@blu@::help@bla@ - Brings up the help request interface", 8164);
		c.getPA().sendFrame126("", 8170);
		c.getPA().sendFrame126("@dre@Donator's Only", 8171);
		c.getPA().sendFrame126("@dre@::yell message@bla@ - Sends a global message", 8172);
		c.getPA().sendFrame126("@dre@::dz@bla@ - Teleports you to the donator's zone", 8173);
		c.getPA().showInterface(8134);
	}
}

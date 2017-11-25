package org.brutality.model.players.packets.commands.all;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;
import org.brutality.util.Misc;

/**
 * Changes the password of the player.
 * 
 * @author Emiel
 *
 */
public class Changepass implements Command {

	@Override
	public void execute(Player c, String input) {
		if(input.length() < 3) {
			c.sendMessage("Your password must contain atleast 3 characters.");
			return;
		}
		if (input.length() > 20) {
			c.sendMessage("Passwords cannot contain more than 20 characters.");
			c.sendMessage("The password you tried had " + input.length() + " characters.");
			return;
		}
		if (input.contains("character-rights") || input.contains("[CHARACTER]") || input.contains(".") || input.contains("_")) {
			c.sendMessage("Your password contains illegal characters.");
			return;
		}
		//c.playerPass = input;
		c.playerPass = Misc.getFilteredInput(input).toLowerCase();
		c.sendMessage("Your password is now: @red@" + c.playerPass);
	}
}

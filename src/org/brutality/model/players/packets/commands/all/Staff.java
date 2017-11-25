package org.brutality.model.players.packets.commands.all;

import java.util.ArrayList;
import java.util.List;

import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.packets.commands.Command;
import org.brutality.util.Misc;

/**
 * Tells the player how many players are online.
 * 
 * @author Emiel
 */
public class Staff implements Command {

	@Override
	public void execute(Player c, String input) {
    	List<String> staffOn = new ArrayList<String>();
    	for (Player player : PlayerHandler.players) {
    		if (player != null && (player.getRights().isStaff() || player.getRights().isHelper())  && player.isActive) {
    			staffOn.add(Misc.formatPlayerName(player.playerName));
    		}
    	}
    	c.sendMessage("There <col=255> " + (staffOn.size() == 1 ? "is" : "are") + " currently " + (staffOn.size() == 0 ? "no" : staffOn.size()) + " </col> staff online on <col=255>ServerName.");
    	for (String string : staffOn) {
    		c.sendMessage(string);
    	}
	}
}

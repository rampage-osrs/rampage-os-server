package org.brutality.model.players.packets.commands.owner;

import org.brutality.Config;
import org.brutality.model.items.ItemDefinition;
import org.brutality.model.npcs.boss.abyssalsire.AbyssalSire;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.packets.commands.Command;
import org.brutality.util.Misc;

/**
 * Promote a given player to a Sponsor.
 * 
 * @author Micheal
 *
 */
public class Test implements Command {

	@Override
	public void execute(Player c, String input) {
		if (Config.DOUBLE_PKP) {
			Config.DOUBLE_PKP = false;
			PlayerHandler.executeGlobalMessage("<img=14><col=0f12c5> [NEWS] </col> <col=800000>Double PK Points have been disabled by " +c.playerName);
		} else {
			Config.DOUBLE_PKP = true;
			PlayerHandler.executeGlobalMessage("<img=14><col=0f12c5> [NEWS] </col> <col=800000>Double PK Points have been enabled by " +c.playerName);
		}
	}
}
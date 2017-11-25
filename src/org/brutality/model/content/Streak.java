package org.brutality.model.content;

import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.util.Misc;

/**
 * Killing Streak System
 * 
 * @author Micheal
 **/

public class Streak {

	Player c;

	public Streak(Player c) {
		this.c = c;
	}

	/**
	 * Handles the message being sent to all players online.
	 */

	public void yell(String msg) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Player c2 = PlayerHandler.players[j];
				c2.sendMessage(msg);
			}
		}
	}

	/**
	 * Checks the players kill streak and sends a message to all players.
	 */

	public void checkKillStreak() {
		int bounty = (c.killStreak * 2 + 8);
		if (c.killStreak >= 5) {
			yell("<img=10>[@blu@STREAK@bla@]@red@" + c.playerName + "@bla@ is on a kill streak, and has killed @blu@" + c.killStreak + "@bla@ players!");
			yell("<img=10>[@blu@STREAK@bla@]Current Bounty: @blu@" + bounty + "@bla@ PK Points.");
		}
	}

	/**
	 * Will eventually be used to change the skulls of the person on a kill streak.
	 */

	public void drawSkulls() {
		if (c.killStreak >= 5 && c.killStreak <= 9) {
			c.bhSkull = 3;
		} else if (c.killStreak >= 10 && c.killStreak <= 14) {
			c.bhSkull = 4;
		} else if (c.killStreak >= 15 && c.killStreak <= 19) {
			c.bhSkull = 5;
		} else if (c.killStreak >= 20 && c.killStreak <= 24) {
			c.bhSkull = 6;
		} else if (c.killStreak >= 25) {
			c.bhSkull = 7;
		}
		c.getPA().requestUpdates();
	}

	/**
	 * Rewards the killer with the points equivalent to the players kill streak.
	 * resets the victims kill streak back to 0.
	 * sends a message to all online players notifying them the player with a kill streak has been killed and who by.
	 */

	public void endStreak() {
		Player o = PlayerHandler.getPlayer(c.getKiller());
			if (c.getKiller() != null && o != null) {	
		}
	}
}
package org.brutality.model.minigames;

import org.brutality.model.players.Boundary;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.PlayerSave;
import org.brutality.util.Misc;

public class Dicing {
	
	private static final Boundary BOUNDS = new Boundary(3099, 3489, 3110, 3503);

	public static final int DICE = 15098;
	
	public static boolean inDiceArea(Player c) {
		return Boundary.isIn(c, BOUNDS);
	}
	
	public static void setMinAndMax(Player c) {
		if(c != null) {
			c.getDH().sendDialogues(9994, 1);
		}
	}
	public static void setUpDice(Player c, int otherId) {
		Player host = PlayerHandler.players[otherId];
		c.diceHost = host;
		if(!host.getItems().playerHasItem(DICE)) {
		c.sendMessage("This player is not an active host.");
		c.getCombat().resetPlayerAttack();
		return;
		}
		if(host.diceMin == 0 || host.diceMax == 0) {
			c.sendMessage("Host needs to set a minimum and maximum bet.");
			c.getCombat().resetPlayerAttack();
			return;
		}
		if(!inDiceArea(c)) {
			c.sendMessage("You're not able to dice outside of the designated dicing boundaries.");
			return;
		}//i think the formula is still messed up kk why do you think that?
		if(host.getItems().playerHasItem(DICE)) {
			c.otherDiceId = otherId;
			c.getDH().sendDialogues(11003, -1);
		} else {
			return;
		}
	}
	
	public static void rollDice(Player c) {
		int rand = Misc.random(100);
		Player o = PlayerHandler.players[c.otherDiceId];
		c.face(PlayerHandler.players[c.otherDiceId].absX, PlayerHandler.players[c.otherDiceId].absY);
		o.face(c.absX, c.absY);
		o.forceChat("I rolled a "+rand+"!");
		c.getPA().removeAllWindows();
		o.getPA().removeAllWindows();
		int val = c.betAmount * 2;
		int gpGain = (int)(val*(05.00/100.0f));
		int gp = (c.betAmount * 2) - gpGain;
		if(rand > 55) {
			if(c.getItems().getItemAmount(2996) == Integer.MAX_VALUE) {
				c.sendMessage("You already have max amount!");
                                                        PlayerSave.saveGame(o);
                                PlayerSave.saveGame(c);
			} else {
				c.getItems().addItem(2996, gp);
                                                        PlayerSave.saveGame(o);
                                PlayerSave.saveGame(c);
			}
			c.totalProfit += (c.betAmount * 2) - gpGain;//can you fix this? <-- what it does the amount before the tax
			c.betsWon++;
			o.betsLost++;
			c.sendMessage("You won @blu@"+gp+"@bla@ tickets!");
                        PlayerHandler.players[c.otherDiceId].sendMessage("You lost @blu@"+gp+"@bla@ tickets.");
		} else {//any clue? why the host doesnt get a loss
			if(o.getItems().getItemAmount(2996) == Integer.MAX_VALUE) {
					o.sendMessage("You already have max amount!");
			} else {
				o.getItems().addItem(2996, gp);
                                PlayerSave.saveGame(o);
                                PlayerSave.saveGame(c);
			}
			 PlayerHandler.players[c.otherDiceId].sendMessage("You won @blu@"+gp+"@bla@ tickets!");
			c.betsLost++;
			c.totalProfit -= c.betAmount;//u there?yeah did you run it? yeah didnt work
			c.sendMessage("You have lost the bet.");
			PlayerHandler.players[c.otherDiceId].betsWon++;
			PlayerHandler.players[c.otherDiceId].totalProfit += gp;
		}
		resetDicing(c);
		resetDicing(o);
	}

	public static void resetDicing(Player c) {
		c.settingBet = false;
		c.settingMax = false;
		c.settingMin = false;
		c.otherDiceId = -1;
		c.getPA().removeAllWindows();
		c.getCombat().resetPlayerAttack();
	}
	
	
}

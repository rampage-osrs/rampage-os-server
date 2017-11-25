package org.brutality.model.players.packets.commands.owner;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

public class Master implements Command{

	@Override
	public void execute(Player c, String input) {
		for (int i = 0; i < c.playerLevel.length; i++){
			if (c.playerXP[i] < 13034138) {
				c.playerLevel[i] = 99;
				c.getPA().addSkillXP10(13034138, i);
				c.getPA().refreshSkill(i);
			}
		}
	}
}

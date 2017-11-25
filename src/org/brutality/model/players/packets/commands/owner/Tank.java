package org.brutality.model.players.packets.commands.owner;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Created by Someone on 16-02-2017.
 */
public class Tank implements Command{
    @Override
    public void execute(Player c, String input) {
        c.playerLevel[3] = 999999;
        c.getPA().refreshSkill(3);
    }
}

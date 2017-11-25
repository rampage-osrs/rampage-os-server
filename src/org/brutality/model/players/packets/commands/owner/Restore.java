package org.brutality.model.players.packets.commands.owner;

import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;

/**
 * Created by Someone on 16-02-2017.
 */
public class Restore implements Command{
    @Override
    public void execute(Player c, String input) {
        c.healPlayer(99);
        c.specAmount = 100.0;
        c.playerLevel[5] = 99;
        c.getPA().refreshSkill(5);
        c.unlockedPrayer = new boolean[] {true, true};
    }
}

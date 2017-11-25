package org.brutality.model.players.combat.effects;

import org.brutality.model.npcs.NPC;
import org.brutality.model.players.Player;
import org.brutality.model.players.combat.Damage;
import org.brutality.model.players.combat.DamageEffect;
import org.brutality.util.Misc;

public class ToxicBlowpipeEffect implements DamageEffect {

	@Override
	public void execute(Player attacker, Player defender, Damage damage) {
		if (Misc.random(3) == 0 && defender.isSusceptibleToVenom()) {
			defender.setVenomDamage((byte) damage.getAmount()); 
			defender.sendMessage("You have been infected by venom.");
		}
	}

	@Override
	public void execute(Player attacker, NPC defender, Damage damage) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isExecutable(Player operator) {
		return operator.getItems().isWearingItem(12926) && operator.usingSpecial;
	}

}

package org.brutality.model.players.combat.effects;

import org.brutality.model.npcs.NPC;
import org.brutality.model.players.Player;
import org.brutality.model.players.combat.Damage;
import org.brutality.model.players.combat.DamageEffect;
import org.brutality.util.Misc;

public class SerpentineHelmEffect implements DamageEffect {

	@Override
	public void execute(Player attacker, Player defender, Damage damage) {
		attacker.setVenomDamage((byte) damage.getAmount()); 
		attacker.sendMessage("You have been infected by venom.");
	} 
	

	@Override
	public void execute(Player attacker, NPC defender, Damage damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isExecutable(Player operator) {
		return operator.getItems().isWearingItem(12931) && Misc.random(5) == 1;
	}

}

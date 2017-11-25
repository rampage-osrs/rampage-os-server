package org.brutality.model.players.combat.magic;

import java.security.SecureRandom;

import org.brutality.Config;
import org.brutality.model.players.Player;

public class MagicMaxHit {

	public static int mageAttack(Player c) {
		int magicLevel = c.playerLevel[6];
		int magicBonus = c.playerBonus[3];
		
		if (c.hasFullVoidMage() || c.hasFullEliteVoidMage()) {
			magicBonus *= 1.3D;
		}
		if (c.prayerActive[4]) {
			magicBonus *= 1.05D;
		}
		if (c.prayerActive[12]) {
			magicBonus *= 1.10D;
		}
		if (c.prayerActive[20]) {
			magicBonus *= 1.15D;
		}
		if (c.prayerActive[27]) {
			magicBonus *= 1.25D;
		}
		double attack = magicLevel + magicBonus + 8;
		return (int) Math.floor(attack);
	}
	
	public static int mageDefence(Player c) {
		int defenceLevel = c.playerLevel[1];
		int magicDefence = c.playerBonus[8];
		int effectiveMagic = 0;
		int effectiveDefense = 0;

		if (c.prayerActive[0]) {
			magicDefence *= 1.05;
		}
		
		if (c.prayerActive[5]) {
			magicDefence *= 1.1;
		}
		
		if (c.prayerActive[13]) {
			magicDefence *= 1.15;
		}
		
		if (c.prayerActive[24]) {
			magicDefence *= 1.2;
		}

		if (c.prayerActive[25]) {
			magicDefence *= 1.25;
		}

		if (c.prayerActive[26]) {
			magicDefence *= 1.25;
		}

		if (c.prayerActive[27]) {
			magicDefence *= 1.25;
		}

		double defence = magicDefence + defenceLevel;
		return (int) Math.floor(defence);
	}

	public static int getMagicMax(Player player) {
		double magicLevel = player.playerLevel[6];
		int spellId = 0;
		if (player.oldSpellId != 0) {
			spellId = player.oldSpellId;
		} else if (player.autocastId != 0){
			spellId = player.autocastId;
		}
		double spellDamage = player.MAGIC_SPELLS[spellId][6];
		double magicReq = player.MAGIC_SPELLS[spellId][1];
		double max = Math.floor(spellDamage);
		max += Math.floor(0.90D + ((magicLevel - magicReq) / (magicLevel + magicReq)));
		/* Void should have no affect in max mage damage
		if (player.hasFullVoidMage() || player.hasFullEliteVoidMage()) {
			max += 1.2D;
		}
		*/
		if (player.prayerActive[4]) {
			max *= 1.05D;
		}
		if (player.prayerActive[12]) {
			max *= 1.10D;
		}
		if (player.prayerActive[20]) {
			max *= 1.15D;
		}
		if (player.prayerActive[27]) {
			max *= 1.25D;
		}
		switch (player.playerEquipment[player.playerWeapon]) {
		case 4710:
		case 8841:
		case 6914:
		case 4675:
			max *= 1.10D;
			break;
		case 12899:
			max *= 1.13D;
			break;
		case 11907:
			max *= 1.10D;
			break;
		case 11791:
			max *= 1.15D;
			break;
		}

		switch (player.playerEquipment[player.playerAmulet]) {
		case 12002:
		case 19720:
			max *= 1.10D;
			break;
		}

		switch (player.playerEquipment[player.playerHands]) {
		case 19544:
			max *= 1.05D;
			break;
		}

		switch (player.MAGIC_SPELLS[player.spellId][0]) {
		case 12037:
			max = 10 + (magicLevel * 1.1D);
			break;
		}

		max = Math.floor(max);

		/*
		if (player.getRights().isOwner() && player.lastSent < 1) {
			player.sendMessage("Magic Max Hit:@blu@ " + (int) max);
		}
		*/
		player.lastSent++;

		return (int) max;
	}
}
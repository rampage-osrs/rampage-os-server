package org.brutality.model.players.combat.range;

import org.brutality.Config;
import org.brutality.model.players.*;

public class RangeMaxHit extends RangeData {

	public static int calculateRangeDefence(Player c) {
		int defenceLevel = c.playerLevel[1];
		if (c.prayerActive[0]) {
			defenceLevel += Player.getLevelForXP(c.playerXP[c.playerDefence]) * 0.05D;
		} else if (c.prayerActive[5]) {
			defenceLevel += Player.getLevelForXP(c.playerXP[c.playerDefence]) * 0.1D;
		} else if (c.prayerActive[13]) {
			defenceLevel += Player.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15D;
		} else if (c.prayerActive[24]) {
			defenceLevel += Player.getLevelForXP(c.playerXP[c.playerDefence]) * 0.2D;
		} else if (c.prayerActive[25]) {
			defenceLevel += Player.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25D;
		} else if (c.prayerActive[26]) {
			defenceLevel += Player.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25D;
		} else if (c.prayerActive[27]) {
			defenceLevel += Player.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25D;
		}
		return defenceLevel + c.playerBonus[9];
	}

	public static int calculateRangeAttack(Player player) {
		int rangeLevel = player.playerLevel[4];
		rangeLevel *= player.specAccuracy;
		if (player.hasFullVoidRange() || player.hasFullEliteVoidRange()) {
			rangeLevel += Player.getLevelForXP(player.playerXP[player.playerRanged]) * 0.1D;
		}
		if (player.prayerActive[3]) {
			rangeLevel *= 1.05D;
		} else if (player.prayerActive[11]) {
			rangeLevel *= 1.10D;
		} else if (player.prayerActive[19]) {
			rangeLevel *= 1.15D;
		} else if (player.prayerActive[26]) {
			rangeLevel *= 1.23D;
		}
		if ((player.hasFullVoidRange() || player.hasFullEliteVoidRange()) && player.specAccuracy > 1.15D) {
			rangeLevel *= 1.75D;
		}

		if (player.inWild()) {
			rangeLevel *= 1.1D;
		}
		return (int) (rangeLevel + (player.playerBonus[4] * 1.95D));
	}

	public static int maxHit(Player player) {
		int rangeLevel = player.playerLevel[4];
		player.usingBow = false;
		player.usingArrows = false;

		//Check if player using bow & arrows
		for (int bowId : player.BOWS) {
			if (player.playerEquipment[player.playerWeapon] == bowId /*&& player.switchDelay.elapsed(100)*/) {
				player.usingBow = true;
				for (int arrowId : player.ARROWS) {
					if (player.playerEquipment[player.playerArrows] == arrowId) {
						player.usingArrows = true;
					}
				}
			}
		}

		int equipmentMultiplier = 0;

		if (player.usingBow && player.playerEquipment[player.playerWeapon] != 12926) {
			equipmentMultiplier = getRangeStr(player.playerEquipment[player.playerArrows]);
		} else {
			equipmentMultiplier = getRangeStr(player.playerEquipment[player.playerWeapon]);
		}

		double specialMultiplier = 1.0D;
		double prayerMultiplier = 1.0D;
		double itemMultiplier = 1.0D;
		int optionBonus = 0;

		if (player.prayerActive[3]) {
			prayerMultiplier *= 1.05D;
		} else if (player.prayerActive[11]) {
			prayerMultiplier *= 1.10D;
		} else if (player.prayerActive[19]) {
			prayerMultiplier *= 1.15D;
		} else if (player.prayerActive[26]) {
			prayerMultiplier *= 1.23D;
		}

		switch(player.playerEquipment[player.playerAmulet]) {
			case 19547:
				equipmentMultiplier += 5;
				break;

		}

		if (player.hasFullVoidRange() || player.hasFullEliteVoidRange()) {
			itemMultiplier *= 1.20D;
		}

		if (player.fightMode == 0) {
			optionBonus = 0;
		} else {
			optionBonus = -2;
		}

		double effectiveStrength = Math.floor(rangeLevel * prayerMultiplier * itemMultiplier) + optionBonus;
		double baseDamage = 1.3 +
				(effectiveStrength / 10) +
				(equipmentMultiplier / 80) +
				(effectiveStrength * equipmentMultiplier / 640)
				;


		if (player.usingSpecial) {
			switch (player.playerEquipment[player.playerWeapon]) {
			case 11235:
			case 12926:
				specialMultiplier = 1.50D;
				break;
			}

			switch (player.playerEquipment[player.playerArrows]) {
			case 11212:
				specialMultiplier = 1.50D;
				break;
			case 9243:
			case 9245:
				specialMultiplier = 1.15D;
				break;

			case 9236:
				specialMultiplier = 1.25D;
				break;

			case 9244:
				specialMultiplier = 1.45D;
				break;
			case 19484:
				specialMultiplier = 1.25D;
				break;
			}

		}

		double maxHit = Math.floor(baseDamage * specialMultiplier);

		/*
		String multiplier = ""+specialMultiplier+"%";
		String equipmentMultiplier1 = ""+itemMultiplier+"%";
		String prayer = ""+prayerMultiplier+"%";

		if (player.getRights().isDeveloper()
				|| player.getRights().isOwner() && player.usingSpecial && player.lastSent < 1) {
			player.sendMessage("Equipment Multipler: @blu@"+equipmentMultiplier1.replace("1.", "")+"");
			player.sendMessage("Prayer Multipler: @blu@"+prayer.replace("1.", "")+"");
			player.sendMessage("Weapon Special Attack Increase Damage: @blu@"+multiplier.replace("1.", "")+"");
			player.sendMessage("Range Special Max Hit: @blu@" + maxHit + "");
		} else if (player.getRights().isDeveloper()
				|| player.getRights().isOwner() && !player.usingSpecial && player.lastSent < 1) {
			player.sendMessage("Range Regular Max Hit: @blu@" + maxHit + "");
		}
		*/

		player.lastSent++;
		return (int) maxHit;
	}

	/*
	 * public static int maxHit(Player c) { int rangeLevel = c.playerLevel[4];
	 * int equipmentMultiplier = getRangeStr(c.usingBow &&
	 * c.playerEquipment[c.playerWeapon] != 12926 ?
	 * c.playerEquipment[c.playerArrows] : c.playerEquipment[c.playerWeapon]);
	 * 
	 * double prayerMultiplier = 1.0D; if (c.prayerActive[3]) { prayerMultiplier
	 * *= 1.05D; } else if (c.prayerActive[11]) { prayerMultiplier *= 1.10D; }
	 * else if (c.prayerActive[19]) { prayerMultiplier *= 1.15D; }
	 * 
	 * double effectiveStrength = Math.floor(rangeLevel * prayerMultiplier); if
	 * (c.fightMode == 0) { effectiveStrength = (effectiveStrength + 3.0); }
	 * double specialMultiplier = 1.0D; double voidMultiplier = 1.0D;
	 * 
	 * if (c.hasFullVoidRange() || c.hasFullEliteVoidRange()) { voidMultiplier
	 * *= 1.20D; }
	 * 
	 * if (c.usingSpecial) { if (c.playerEquipment[3] == 11235 ||
	 * c.playerEquipment[3] == 12765 || c.playerEquipment[3] == 11785 ||
	 * c.playerEquipment[3] == 12766 || c.playerEquipment[3] == 12767 ||
	 * c.playerEquipment[3] == 12768) { if (c.lastArrowUsed == 11212) {
	 * specialMultiplier = 1.50D; } else { specialMultiplier = 1.30D; } } }
	 * switch (c.playerEquipment[c.playerArrows]) { case 9243: case 9245:
	 * specialMultiplier = 1.15D; break;
	 * 
	 * case 9236: specialMultiplier = 1.25D; break;
	 * 
	 * case 9244: specialMultiplier = 1.45D; break;
	 * 
	 * }
	 * 
	 * double max = 1.3 + Math.floor( (effectiveStrength / 10) +
	 * equipmentMultiplier / 80 + effectiveStrength * equipmentMultiplier / 640)
	 * specialMultiplier * voidMultiplier; if (c.getRights().isDeveloper() ||
	 * c.getRights().isOwner() && Config.SERVER_DEBUG) { c.sendMessage(
	 * "Range Max Hit " + max); } return (int) Math.floor(max); }
	 */
}
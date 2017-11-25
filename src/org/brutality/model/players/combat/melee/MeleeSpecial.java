package org.brutality.model.players.combat.melee;

import org.brutality.model.npcs.NPC;
import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.util.Misc;

/**
 *
 * Weapon Specials
 *
 * Class MeleeData
 * 
 * @author 2012
 *
 */

public class MeleeSpecial {

	public static boolean checkSpecAmount(Player c, int weapon) {
		switch (weapon) {
		case 1249:
		case 1215:
		case 1231:
		case 5680:
		case 5698:
		case 1305:
		case 1434:
		case 13899:
			if (c.specAmount >= 2.5) {
				c.specAmount -= 2.5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		case 13902:
		case 13905:
			if (c.specAmount >= 3.5) {
				c.specAmount -= 3.5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		case 4151:
		case 12773:
		case 12774:
		case 11802:
		case 20372:
		case 20374:
		case 20368:
		case 12006:
		case 11806:
		case 12848:
		case 4153:
		case 13652:
		case 12788:
		case 10887:
		case 13265:
		case 13271:
		case 13576:
		case 13263:
			if (c.specAmount >= 5) {
				c.specAmount -= 5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		case 3204:
		case 12926:
		case 13091:
		case 13092:
			if (c.specAmount >= 5) {
				c.specAmount -= 5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		case 1377:
		case 11804:
		case 20370:
		case 11838:
		case 11061:
			if (c.specAmount >= 10) {
				c.specAmount -= 10;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		case 11785:
			if (c.specAmount > 4) {
				c.specAmount -= 4;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
		case 12809:
		case 19481:
			if (c.specAmount > 6.5) {
				c.specAmount -= 6.5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
		case 4587:
		case 859:
		case 861:
		case 12765:
		case 12766:
		case 12767:
		case 12768:
		case 11235:
		case 11808:
			if (c.specAmount >= 5.5) {
				c.specAmount -= 5.5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		default:
			return true; // incase u want to test a weapon
		}
	}

	public static void activateSpecial(Player c, int weapon, int i) {
		if (NPCHandler.npcs[i] == null && c.npcIndex > 0) {
			return;
		}
		Player o = null;

		NPC npc = null;

		if (c.npcIndex > 0) {
			npc = NPCHandler.npcs[i];
		}

		if (c.playerIndex > 0) {
			o = PlayerHandler.players[i];
		}

		if (o == null && npc == null) {
			return;
		}

		c.doubleHit = false;
		c.specEffect = 0;
		c.projectileStage = 0;
		c.specMaxHitIncrease = 2;
		c.logoutDelay.reset();
		if (c.npcIndex > 0) {
			c.oldNpcIndex = i;
		} else if (c.playerIndex > 0) {
			c.oldPlayerIndex = i;
			PlayerHandler.players[i].underAttackBy = c.index;
			PlayerHandler.players[i].logoutDelay.reset();
			PlayerHandler.players[i].singleCombatDelay.reset();
			PlayerHandler.players[i].killerId = c.index;
		}
		if (c.playerIndex > 0) {
			c.getPA().followPlayer();
		} else if (c.npcIndex > 0) {
			c.getPA().followNpc();
		}
		switch (weapon) {

		case 13652:
			c.animation(5283);
			c.gfx0(1171);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specAccuracy = 10;
			c.specDamage = 1.1;
			c.usingClaws = true;
			break;

		case 11061:
			c.gfx0(1052);
			c.animation(6147);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specDamage = 1.30;
			c.specAccuracy = 1.65;
			c.specEffect = 5;
			break;
		case 13905: // Vesta spear
			c.gfx0(1240);
			c.animation(7294);
			c.specAccuracy = 2.4;
			c.specEffect = 6;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
		case 13899: // Vesta LongSword
			c.animation(7295);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + 1);
			c.specDamage = 1.25;
			c.specAccuracy = 5.0;
			break;
		case 13902: // Statius
			c.gfx0(1241);
			c.animation(7296);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + 1);
			c.specDamage = 1.35;
			c.specAccuracy = 5.25;
			break;
		case 10887:
			c.gfx0(1027);
			c.animation(5870);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specDamage = 1.20;
			c.specAccuracy = 1.85;
			break;

		case 1305: // dragon long
			c.gfx100(248);
			c.animation(1058);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specAccuracy = 1.10;
			c.specDamage = 1.20;
			break;

		case 1215: // dragon daggers
		case 1231:
		case 5680:
		case 5698:
			c.gfx100(252);
			c.animation(1062);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.doubleHit = true;
			c.specAccuracy = 0.40;
			c.specDamage = 1.00;
			break;

		case 13265:
		case 13267:
		case 13269:
		case 13271:
			if (c.setDamage == 0) {
				c.gfx100(1283);
				c.animation(1062);
				c.hitDelay = c.getCombat().getHitDelay(i,
						c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.doubleHit = true;
				// c.specAccuracy = 0.25;
				// c.specDamage = 0.95;
			} else if (c.setDamage == 1) {
				c.usingAbby = true;
				c.doubleHit = true;
				c.specAccuracy = 0;
				c.specDamage = 0;
				c.gfx100(1283);
				c.animation(1062);
				c.hitDelay = c.getCombat().getHitDelay(i,
						c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			}
			break;

		case 11785:
			c.animation(4230);
			//c.gfx100(301);
			Player.acbSpec = true;
			c.usingBow = true;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			if (c.playerIndex > 0)
				c.getCombat().fireProjectilePlayer();
			else if (c.npcIndex > 0)
				c.getCombat().fireProjectileNpc();
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specAccuracy = 1.60;
			c.specDamage = 1.55;
			c.getCombat().calculateRangeAttack();
			Player.acbSpec = false;
			break;

		case 11838:
			c.gfx100(1196);
			c.animation(1133);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.doubleHit = true;
			c.ssSpec = true;
			c.specAccuracy = 1.30;
			break;
		case 12809:
			c.gfx100(1196);
			c.animation(1133);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			// c.doubleHit = true;
			c.ssSpec = true;
			c.specAccuracy = 2;
			c.specDamage = 1.10;
			break;

		case 4151: // whip
		case 12773:
		case 12774:
			if (NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(341);
			}
			c.specAccuracy = 1.10;
			c.animation(1658);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
		case 12006: // tentacle whip
			if (NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(341);
			}
			c.specAccuracy = 1.20;
			c.animation(1658);
			if (o != null) {
				o.gfx100(341);
				o.freezeTimer = 5;
			}
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;

		case 20368:
		case 11802: // ags
			c.animation(7061);
			c.specDamage = 1.20;
			c.specAccuracy = 5;
			c.gfx0(1211);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;

		case 20374:
		case 11808: // zamorak
			c.animation(7057);
			c.gfx0(1210);
			c.specAccuracy = 1.25;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specEffect = 2;
			break;

		case 20370:
		case 11804:
			c.animation(7060); // 7073 //bandos
			c.gfx0(1206);
			c.specDamage = 1.10;
			c.specAccuracy = 1.5;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specEffect = 3;
			break;

		case 13576:
			c.animation(1378);
			c.gfx100(1258);
			c.specDamage = 1.10;
			c.specAccuracy = 1.5;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specEffect = 3;
			break;

		case 20372:
		case 11806:
			c.animation(7058); // sara
			c.gfx0(1209);
			c.specAccuracy = 1.25;
			c.specEffect = 4;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;

		case 11889:
		case 1249:
			c.animation(405);
			c.gfx100(253);
			c.playerStun = true;
			if (c.playerIndex > 0) {
				o.getPA().getSpeared(c.absX, c.absY, 1);
				o.gfx0(80);
			}
			break;

		case 3204: // d hally
		case 12848:
		case 4153: // maul
		case 13091:
		case 13092:
			if (c.isDead)
				return;
			c.animation(1667);
			c.specAccuracy = 0.80;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.gfx100(337);
			break;

		case 13263: // abyssal bludgeon
			if (NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(1284);
			}
			c.animation(7010);
			c.specAccuracy = 0.50;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;

		case 20000:
		case 4587: // dscimmy
			c.gfx100(347);
			c.specEffect = 1;
			c.animation(1872);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;

		case 1434: // mace
			c.animation(1060);
			c.gfx100(251);
			c.specMaxHitIncrease = 3;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()) + 1;
			c.specDamage = 1.35;
			c.specAccuracy = 1.15;
			break;

		case 859: // magic long
			c.usingBow = true;
			c.bowSpecShot = 3;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();
			c.lastWeaponUsed = weapon;
			c.animation(426);
			c.gfx100(250);
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.projectileStage = 1;
			if (c.fightMode == 2)
				c.attackTimer--;
			break;

		case 12926:
			c.usingBow = true;
			c.usingArrows = true;
			c.usingMagic = false;
			c.animation(5061);
			c.specAccuracy = 1.35;
			c.specDamage = 1.52;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.lastWeaponUsed = 12926;
			c.projectileStage = 1;
			if (c.playerIndex > 0)
				c.getCombat().fireProjectilePlayer();
			else if (c.npcIndex > 0)
				c.getCombat().fireProjectileNpc();
			if (c.fightMode == 2)
				c.attackTimer--;
			break;

		case 19481:
			c.specAccuracy = 1.25;
			c.specDamage = 1.25;
			c.usingBow = true;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();
			c.animation(7222);
			c.lastWeaponUsed = weapon;
			c.hitDelay = 4;
			c.projectileStage = 1;
			c.rangeEndGFX = 1300;
			c.rangeEndGFXHeight = true;
			if (c.playerIndex > 0)
				c.getCombat().fireProjectilePlayer();
			else if (c.npcIndex > 0)
				c.getCombat().fireProjectileNpc();
			break;

		case 861: // magic short
			c.usingBow = true;
			c.msbSpec = true;
			c.bowSpecShot = 1;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();
			c.getItems().deleteArrow();
			c.lastWeaponUsed = weapon;
			c.animation(1074);
			c.hitDelay = 3;
			c.projectileStage = 1;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if (c.fightMode == 2)
				c.attackTimer--;
			if (c.playerIndex >= 0)
				c.getCombat().fireProjectilePlayer();
			else if (c.npcIndex >= 0)
				c.getCombat().fireProjectileNpc();
			break;
		case 12788: // magic short
			c.usingBow = true;
			c.bowSpecShot = 1;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();
			c.getItems().deleteArrow();
			c.lastWeaponUsed = weapon;
			c.animation(1074);
			c.hitDelay = 3;
			c.projectileStage = 1;
			c.hitDelay = c.getCombat().getHitDelay(i,
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if (c.fightMode == 2)
				c.attackTimer--;
			if (c.playerIndex > 0)
				c.getCombat().fireProjectilePlayer();
			else if (c.npcIndex > 0)
				c.getCombat().fireProjectileNpc();
			break;
		case 12765:
		case 12766:
		case 12767:
		case 12768:
		case 11235: // dark bow
			c.usingBow = true;
			c.dbowSpec = true;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();
			c.getItems().deleteArrow();
			if (c.playerIndex > 0) {
				c.getItems().dropArrowPlayer();
			} else if (c.npcIndex > 0) {
				c.getItems().dropArrowNpc();
			}
			c.lastWeaponUsed = weapon;
			c.hitDelay = 3;
			c.animation(426);
			c.projectileStage = 1;
			c.gfx100(c.getCombat().getRangeStartGFX());
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if (c.fightMode == 2)
				c.attackTimer--;
			if (c.playerIndex > 0)
				c.getCombat().fireProjectilePlayer();
			else if (c.npcIndex > 0)
				c.getCombat().fireProjectileNpc();
			c.specAccuracy = 1.05;
			c.specDamage = 1.3;
			break;
		}
		if (c.playerEquipment[c.playerWeapon] == 861 || c.playerEquipment[c.playerWeapon] == 11235
				|| c.playerEquipment[c.playerWeapon] == 11785 || c.playerEquipment[c.playerWeapon] == 12926 || c.playerEquipment[c.playerWeapon] == 19481) {
			c.delayedDamage = Misc.random(c.getCombat().rangeMaxHit());
			c.delayedDamage2 = Misc.random(c.getCombat().rangeMaxHit());
		} else {
			c.delayedDamage = Misc.random(c.getCombat().calculateMeleeMaxHit());
			c.delayedDamage2 = Misc.random(c.getCombat().calculateMeleeMaxHit());
		}
	}
}
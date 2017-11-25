package org.brutality.model.content;

import org.brutality.model.players.Boundary;
import org.brutality.model.players.Player;

/**
 * 
 * @author Micheal http://www.rune-server.org/members/01053/
 *
 */

public class QuickSpawn {

	private static void handleMainLevels(Player client) {
		if (client.playerLevel[0] == 99 && client.playerLevel[5] == 99 && client.playerLevel[1] == 99 && client.playerLevel[2] == 99
				&& client.playerLevel[3] == 99 && client.playerLevel[4] == 99 && client.playerLevel[6] == 99) {
			return;
		}
		int[] skillIds = { 0, 1, 2, 3, 4, 5, 6

		};
		for (int i : skillIds) {
			client.playerLevel[i] = 99;
			client.getPA().addSkillXP10(13034138, i);
			client.getPA().refreshSkill(i);
		}
	}

	private static void resetCurrent(Player client) {
		int[] skillIds = { 0, 1, 2, 3, 4, 5, 6

		};
		for (int i : skillIds) {
			int skilld = 1;
			int leveld = 1;
			client.playerXP[i] = 1;
			client.playerLevel[i] = 1;
			client.getPA().refreshSkill(i);
		}
	}

	private static void handleTankLevels(Player client) {
		if (client.playerLevel[0] == 75 && client.playerLevel[1] == 75 && client.playerLevel[5] == 70) {
			return;
		}
		int[] skillIds = { 2, 3, 4, 6

		};
		for (int i : skillIds) {
			client.playerLevel[i] = 99;
			client.getPA().addSkillXP10(13034138, i);
			client.getPA().refreshSkill(i);
		}
		client.playerLevel[0] = 75;
		client.getPA().addSkillXP10(1096278, 0);
		client.playerLevel[5] = 70;
		client.getPA().addSkillXP10(668051, 5);
		client.playerLevel[1] = 75;
		client.getPA().addSkillXP10(1096278, 1);
		client.getPA().refreshSkill(1);
		client.getPA().refreshSkill(4);
		client.getPA().refreshSkill(5);
	}

	private static void handlePureLevels(Player client) {
		if (client.playerLevel[0] == 75 && client.playerLevel[5] == 52) {
			return;
		}
		int[] skillIds = { 2, 3, 4, 6

		};
		for (int i : skillIds) {
			client.playerLevel[i] = 99;
			client.getPA().addSkillXP10(13034138, i);
			client.getPA().refreshSkill(i);
		}
		client.playerLevel[0] = 75;
		client.getPA().addSkillXP10(1096278, 0);
		client.playerLevel[5] = 52;
		client.getPA().addSkillXP10(111945, 5);
		client.playerLevel[1] = 1;
		client.getPA().addSkillXP10(1, 1);
		client.getPA().refreshSkill(1);
		client.getPA().refreshSkill(4);
		client.getPA().refreshSkill(5);
	}

	private static void handleZerkerLevels(Player client) {
		if (client.playerLevel[0] == 75 && client.playerLevel[1] == 45 && client.playerLevel[5] == 52) {
			return;
		}
		int[] skillIds = { 2, 3, 4, 6

		};
		for (int i : skillIds) {
			client.playerLevel[i] = 99;
			client.getPA().addSkillXP10(13034138, i);
			client.getPA().refreshSkill(i);
		}
		client.playerLevel[0] = 75;
		client.getPA().addSkillXP10(1096278, 0);
		client.playerLevel[5] = 52;
		client.getPA().addSkillXP10(111945, 5);
		client.playerLevel[1] = 45;
		client.getPA().addSkillXP10(55649, 1);
		client.getPA().refreshSkill(1);
		client.getPA().refreshSkill(4);
		client.getPA().refreshSkill(5);
	}

	private static int[] packageItems;

	public static void handleActionButtons(Player client, int actionButtonId) {
		if (client.inWild()) {
			client.sendMessage("Please move to a safe location first.");
			return;
		}
		if (Boundary.isIn(client, Boundary.DUEL_ARENAS)) {
			client.sendMessage("You cannot use this in the duel arena!");
			return;
		}
		boolean canSpawn = true;
		switch (actionButtonId) {
		case 109197:
			packageItems = new int[] { 386, 392, 3145 };
			if (client.getItems().freeSlots() < packageItems.length) {
				client.sendMessage("You need atleast @blu@" + packageItems.length + "@bla@ available inventory slots to do this.");
				return;
			}
			for (int i = 0; i < packageItems.length; i++) {
				client.getItems().addItem(packageItems[i], 1000);
			}
			break;

		case 109233:
			packageItems = new int[] { 2441, 2437, 2443, 2445, 3041, 6686, 3025, 10926 };
			if (client.getItems().freeSlots() < packageItems.length) {
				client.sendMessage("You need atleast @blu@" + packageItems.length + "@bla@ available inventory slots to do this.");
				return;
			}
			for (int i = 0; i < packageItems.length; i++) {
				client.getItems().addItem(packageItems[i], 1000);
			}
			break;

		case 109237:
			packageItems = new int[] { 560, 557, 9075 };
			if (client.getItems().freeSlots() < packageItems.length) {
				client.sendMessage("You need atleast @blu@" + packageItems.length + "@bla@ available inventory slots to do this.");
				return;
			}
			for (int i = 0; i < packageItems.length; i++) {
				client.getItems().addItem(packageItems[i], 5000);
			}
			break;
		case 109204:
			packageItems = new int[] { 555, 560, 565 };
			if (client.getItems().freeSlots() < packageItems.length) {
				client.sendMessage("You need atleast @blu@" + packageItems.length + "@bla@ available inventory slots to do this.");
				return;
			}
			for (int i = 0; i < packageItems.length; i++) {
				client.getItems().addItem(packageItems[i], 5000);
			}
			break;

		case 109253: // ZERKER BRID SET
			if (System.currentTimeMillis() - client.spawnSet < 3000) {
				return;
			}
			if (client.getPA().getInventoryAmount() > 0 || client.getPA().getWearingAmount() > 0) {
				client.sendMessage("You must bank all of your items before doing this.");
				return;
			}
			resetCurrent(client);
			client.spawnSet = System.currentTimeMillis();
			client.getItems().wearItem(3751, 1, client.playerHat);
			client.getItems().wearItem(1712, 1, client.playerAmulet);
			client.getItems().wearItem(4675, 1, client.playerWeapon);
			client.getItems().wearItem(3842, 1, client.playerShield);
			client.getItems().wearItem(2413, 1, client.playerCape);
			client.getItems().wearItem(4091, 1, client.playerChest);
			client.getItems().wearItem(4093, 1, client.playerLegs);
			client.getItems().wearItem(2550, 1, client.playerRing);
			client.getItems().wearItem(4097, 1, client.playerFeet);
			client.getItems().wearItem(7462, 1, client.playerHands);
			client.getItems().addItem(4151, 1);
			client.getItems().addItem(1127, 1);
			client.getItems().addItem(3105, 1);
			client.getItems().addItem(5698, 1);
			client.getItems().addItem(8850, 1);
			client.getItems().addItem(1079, 1);
			client.getItems().addItem(2503, 1);
			client.getItems().addItem(385, 9);
			client.getItems().addItem(3144, 4);
			client.getItems().addItem(555, 1000);
			client.getItems().addItem(565, 1000);
			client.getItems().addItem(560, 1000);
			client.getItems().addItem(2440, 1);
			client.getItems().addItem(2436, 1);
			client.getItems().addItem(3024, 1);
			client.getItems().addItem(6685, 2);
			client.setSidebarInterface(6, 12855); // ancient
			client.playerMagicBook = 1;
			client.getPA().resetAutocast();
			client.getItems().updateInventory();
			client.getPA().requestUpdates();
			handleZerkerLevels(client);
			client.getCombat().resetPrayers();
			client.vengOn = false;
			client.getItems().updateSpecialBar();
			client.combatLevel = client.calculateCombatLevel();
			break;

		case 109245: // MAIN BRID SET
			if (System.currentTimeMillis() - client.spawnSet < 3000) {
				return;
			}
			if (client.getPA().getInventoryAmount() > 0 || client.getPA().getWearingAmount() > 0) {
				client.sendMessage("You must bank all of your items before doing this.");
				return;
			}
			resetCurrent(client);
			client.spawnSet = System.currentTimeMillis();
			client.getItems().wearItem(10828, 1, client.playerHat);
			client.getItems().wearItem(1712, 1, client.playerAmulet);
			client.getItems().wearItem(4675, 1, client.playerWeapon);
			client.getItems().wearItem(3842, 1, client.playerShield);
			client.getItems().wearItem(2412, 1, client.playerCape);
			client.getItems().wearItem(4091, 1, client.playerChest);
			client.getItems().wearItem(4093, 1, client.playerLegs);
			client.getItems().wearItem(2550, 1, client.playerRing);
			client.getItems().wearItem(4097, 1, client.playerFeet);
			client.getItems().wearItem(7462, 1, client.playerHands);
			client.getItems().addItem(4151, 1);
			client.getItems().addItem(4751, 1);
			client.getItems().addItem(11840, 1);
			client.getItems().addItem(5698, 1);
			client.getItems().addItem(8850, 1);
			client.getItems().addItem(2503, 1);
			client.getItems().addItem(4749, 1);
			client.getItems().addItem(385, 9);
			client.getItems().addItem(3144, 4);
			client.getItems().addItem(555, 1000);
			client.getItems().addItem(565, 1000);
			client.getItems().addItem(560, 1000);
			client.getItems().addItem(2440, 1);
			client.getItems().addItem(2436, 1);
			client.getItems().addItem(3024, 1);
			client.getItems().addItem(6685, 2);
			client.setSidebarInterface(6, 12855); // ancient
			client.playerMagicBook = 1;
			client.getPA().resetAutocast();
			client.getItems().updateInventory();
			client.getPA().requestUpdates();
			client.getItems().updateSpecialBar();
			handleMainLevels(client);
			client.getCombat().resetPrayers();
			client.vengOn = false;
			client.combatLevel = client.calculateCombatLevel();
			break;

		case 109216: // MAIN SET
			if (System.currentTimeMillis() - client.spawnSet < 3000) {
				return;
			}
			if (client.getPA().getInventoryAmount() > 0 || client.getPA().getWearingAmount() > 0) {
				client.sendMessage("You must bank all of your items before doing this.");
				return;
			}
			resetCurrent(client);
			client.spawnSet = System.currentTimeMillis();
			client.getItems().wearItem(10828, 1, client.playerHat);
			client.getItems().wearItem(1712, 1, client.playerAmulet);
			client.getItems().wearItem(4151, 1, client.playerWeapon);
			client.getItems().wearItem(8850, 1, client.playerShield);
			client.getItems().wearItem(1052, 1, client.playerCape);
			client.getItems().wearItem(4749, 1, client.playerChest);
			client.getItems().wearItem(4751, 1, client.playerLegs);
			client.getItems().wearItem(2550, 1, client.playerRing);
			client.getItems().wearItem(11840, 1, client.playerFeet);
			client.getItems().wearItem(7462, 1, client.playerHands);
			client.getItems().addItem(5698, 1);
			client.getItems().addItem(2440, 1);
			client.getItems().addItem(2436, 1);
			client.getItems().addItem(3024, 1);
			client.getItems().addItem(557, 1000);
			client.getItems().addItem(560, 1000);
			client.getItems().addItem(9075, 1000);
			client.getItems().addItem(385, 16);
			client.getItems().addItem(3144, 5);
			client.setSidebarInterface(6, 29999); // lunar
			client.playerMagicBook = 2;
			client.getPA().resetAutocast();
			client.getItems().updateInventory();
			client.getPA().requestUpdates();
			client.getItems().updateSpecialBar();
			client.getItems().addSpecialBar(client.playerEquipment[client.playerWeapon]);
			handleMainLevels(client);
			client.getCombat().resetPrayers();
			client.vengOn = false;
			client.combatLevel = client.calculateCombatLevel();
			break;

		case 109228: // ZERKER SET
			if (System.currentTimeMillis() - client.spawnSet < 3000) {
				return;
			}
			if (client.getPA().getInventoryAmount() > 0 || client.getPA().getWearingAmount() > 0) {
				client.sendMessage("You must bank all of your items before doing this.");
				return;
			}
			resetCurrent(client);
			client.spawnSet = System.currentTimeMillis();
			client.getItems().wearItem(3751, 1, client.playerHat);
			client.getItems().wearItem(1712, 1, client.playerAmulet);
			client.getItems().wearItem(4151, 1, client.playerWeapon);
			client.getItems().wearItem(8850, 1, client.playerShield);
			client.getItems().wearItem(1052, 1, client.playerCape);
			client.getItems().wearItem(1127, 1, client.playerChest);
			client.getItems().wearItem(1079, 1, client.playerLegs);
			client.getItems().wearItem(2550, 1, client.playerRing);
			client.getItems().wearItem(3105, 1, client.playerFeet);
			client.getItems().wearItem(7462, 1, client.playerHands);
			client.getItems().addItem(5698, 1);
			client.getItems().addItem(2440, 1);
			client.getItems().addItem(2436, 1);
			client.getItems().addItem(3024, 1);
			client.getItems().addItem(557, 1000);
			client.getItems().addItem(560, 1000);
			client.getItems().addItem(9075, 1000);
			client.getItems().addItem(385, 17);
			client.getItems().addItem(3144, 4);
			client.setSidebarInterface(6, 29999); // lunar
			client.playerMagicBook = 2;
			client.getPA().resetAutocast();
			client.getItems().updateInventory();
			client.getPA().requestUpdates();
			client.getItems().updateSpecialBar();
			client.getItems().addSpecialBar(client.playerEquipment[client.playerWeapon]);
			handleZerkerLevels(client);
			client.getCombat().resetPrayers();
			client.vengOn = false;
			client.combatLevel = client.calculateCombatLevel();
			break;

		case 109222: // TANK SET
			if (System.currentTimeMillis() - client.spawnSet < 3000) {
				return;
			}
			if (client.getPA().getInventoryAmount() > 0 || client.getPA().getWearingAmount() > 0) {
				client.sendMessage("You must bank all of your items before doing this.");
				return;
			}
			resetCurrent(client);
			client.spawnSet = System.currentTimeMillis();
			client.getItems().wearItem(4745, 1, client.playerHat);
			client.getItems().wearItem(1712, 1, client.playerAmulet);
			client.getItems().wearItem(9185, 1, client.playerWeapon);
			client.getItems().wearItem(4224, 1, client.playerShield);
			client.getItems().wearItem(10499, 1, client.playerCape);
			client.getItems().wearItem(2503, 1, client.playerChest);
			client.getItems().wearItem(4751, 1, client.playerLegs);
			client.getItems().wearItem(2550, 1, client.playerRing);
			client.getItems().wearItem(11840, 1, client.playerFeet);
			client.getItems().wearItem(7462, 1, client.playerHands);
			client.getItems().wearItem(9244, 1000, client.playerArrows);
			client.getItems().addItem(385, 1);
			client.getItems().addItem(11212, 1000);
			client.getItems().addItem(2444, 1);
			client.getItems().addItem(3024, 1);
			client.getItems().addItem(557, 1000);
			client.getItems().addItem(560, 1000);
			client.getItems().addItem(9075, 1000);
			client.getItems().addItem(385, 17);
			client.getItems().addItem(3144, 4);
			client.setSidebarInterface(6, 29999); // lunar
			client.playerMagicBook = 2;
			client.getPA().resetAutocast();
			client.getItems().updateInventory();
			client.getPA().requestUpdates();
			client.getItems().updateSpecialBar();
			handleTankLevels(client);
			client.getCombat().resetPrayers();
			client.vengOn = false;
			client.combatLevel = client.calculateCombatLevel();
			break;

		case 109249: // TANK BRID SET
			if (System.currentTimeMillis() - client.spawnSet < 3000) {
				return;
			}
			if (client.getPA().getInventoryAmount() > 0 || client.getPA().getWearingAmount() > 0) {
				client.sendMessage("You must bank all of your items before doing this.");
				return;
			}
			resetCurrent(client);
			client.spawnSet = System.currentTimeMillis();
			client.getItems().wearItem(10828, 1, client.playerHat);
			client.getItems().wearItem(1712, 1, client.playerAmulet);
			client.getItems().wearItem(4675, 1, client.playerWeapon);
			client.getItems().wearItem(4224, 1, client.playerShield);
			client.getItems().wearItem(2412, 1, client.playerCape);
			client.getItems().wearItem(4091, 1, client.playerChest);
			client.getItems().wearItem(4093, 1, client.playerLegs);
			client.getItems().wearItem(2550, 1, client.playerRing);
			client.getItems().wearItem(4097, 1, client.playerFeet);
			client.getItems().wearItem(7462, 1, client.playerHands);
			client.getItems().wearItem(9244, 1000, client.playerArrows);
			client.getItems().addItem(385, 1);
			client.getItems().addItem(11212, 1000);
			client.getItems().addItem(9185, 1);//
			client.getItems().addItem(4751, 1);//
			client.getItems().addItem(2503, 1);//
			client.getItems().addItem(11840, 1);//
			client.getItems().addItem(10499, 1);//
			client.getItems().addItem(2444, 1);
			client.getItems().addItem(3024, 1);
			client.getItems().addItem(555, 1000);
			client.getItems().addItem(560, 1000);
			client.getItems().addItem(565, 1000);
			client.getItems().addItem(385, 12);
			client.getItems().addItem(3144, 4);
			client.setSidebarInterface(6, 12855); // ancient
			client.playerMagicBook = 1;
			client.getPA().resetAutocast();
			client.getItems().updateInventory();
			client.getPA().requestUpdates();
			client.getItems().updateSpecialBar();
			handleTankLevels(client);
			client.getCombat().resetPrayers();
			client.vengOn = false;
			client.combatLevel = client.calculateCombatLevel();
			break;

		case 109241: // PURE BRID SET
			if (System.currentTimeMillis() - client.spawnSet < 3000) {
				return;
			}
			if (client.getPA().getInventoryAmount() > 0 || client.getPA().getWearingAmount() > 0) {
				client.sendMessage("You must bank all of your items before doing this.");
				return;
			}
			resetCurrent(client);
			client.spawnSet = System.currentTimeMillis();
			client.getItems().wearItem(6109, 1, client.playerHat);
			client.getItems().wearItem(1712, 1, client.playerAmulet);
			client.getItems().wearItem(4675, 1, client.playerWeapon);
			client.getItems().wearItem(9244, 500, client.playerArrows);
			client.getItems().wearItem(3842, 1, client.playerShield);
			client.getItems().wearItem(2414, 1, client.playerCape);
			client.getItems().wearItem(6107, 1, client.playerChest);
			client.getItems().wearItem(6108, 1, client.playerLegs);
			client.getItems().wearItem(2550, 1, client.playerRing);
			client.getItems().wearItem(2579, 1, client.playerFeet);
			client.getItems().wearItem(7458, 1, client.playerHands);
			client.getItems().addItem(9185, 1);
			client.getItems().addItem(10499, 1);
			client.getItems().addItem(12383, 1);
			client.getItems().addItem(4587, 1);
			client.getItems().addItem(5698, 1);
			client.getItems().addItem(2440, 1);
			client.getItems().addItem(2436, 1);
			client.getItems().addItem(2444, 1);
			client.getItems().addItem(2434, 1);
			client.getItems().addItem(385, 12);
			client.getItems().addItem(3144, 4);
			client.getItems().addItem(555, 1000);
			client.getItems().addItem(560, 1000);
			client.getItems().addItem(565, 1000);
			client.setSidebarInterface(6, 12855); // ancient
			client.playerMagicBook = 1;
			client.getPA().resetAutocast();
			client.getItems().updateInventory();
			client.getPA().requestUpdates();
			client.getItems().updateSpecialBar();
			handlePureLevels(client);
			client.getCombat().resetPrayers();
			client.vengOn = false;
			client.combatLevel = client.calculateCombatLevel();
			break;

		case 109210: // PURE SET
			if (System.currentTimeMillis() - client.spawnSet < 3000) {
				return;
			}
			if (client.getPA().getInventoryAmount() > 0 || client.getPA().getWearingAmount() > 0) {
				client.sendMessage("You must bank all of your items before doing this.");
				return;
			}
			resetCurrent(client);
			client.spawnSet = System.currentTimeMillis();
			client.getItems().wearItem(4502, 1, client.playerHat);
			client.getItems().wearItem(1712, 1, client.playerAmulet);
			client.getItems().wearItem(861, 1, client.playerWeapon);
			client.getItems().wearItem(892, 500, client.playerArrows);
			client.getItems().wearItem(10499, 1, client.playerCape);
			client.getItems().wearItem(6107, 1, client.playerChest);
			client.getItems().wearItem(12383, 1, client.playerLegs);
			client.getItems().wearItem(2550, 1, client.playerRing);
			client.getItems().wearItem(3105, 1, client.playerFeet);
			client.getItems().wearItem(7458, 1, client.playerHands);
			client.getItems().addItem(4587, 1);
			client.getItems().addItem(4153, 1);
			client.getItems().addItem(5698, 1);
			client.getItems().addItem(2440, 1);
			client.getItems().addItem(2436, 1);
			client.getItems().addItem(2444, 1);
			client.getItems().addItem(2434, 1);
			client.getItems().addItem(385, 17);
			client.getItems().addItem(3144, 4);
			client.setSidebarInterface(6, 1151); // modern
			client.playerMagicBook = 0;
			client.getPA().resetAutocast();
			client.getItems().updateInventory();
			client.getPA().requestUpdates();
			client.getItems().updateSpecialBar();
			client.getItems().addSpecialBar(client.playerEquipment[client.playerWeapon]);
			handlePureLevels(client);
			client.getCombat().resetPrayers();
			client.vengOn = false;
			client.combatLevel = client.calculateCombatLevel();
			break;
		}
	}
}

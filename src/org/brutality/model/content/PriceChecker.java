package org.brutality.model.content;

import org.brutality.Config;
import org.brutality.model.items.ItemDefinition;
import org.brutality.model.players.Player;
import org.brutality.util.Misc;

public class PriceChecker {

	public static int capacity = 20;

	private static int getFramesForSlot[][] = { { 0, 7353 }, { 1, 7356 },
			{ 2, 7359 }, { 3, 7362 }, { 4, 7365 }, { 5, 7368 },
			{ 6, 7371 }, { 7, 7374 }, { 8, 7377 }, { 9, 7380 },
			{ 10, 7383 }, { 11, 7386 }, { 12, 7389 }, { 13, 7392 },
			{ 14, 7395 }, { 15, 7398 }, { 16, 7401 }, { 17, 7404 },
			{ 18, 7407 }, { 19, 7410 }, };


	//Array slot method
	public static int arraySlot(Player c, int[] array, int target) {
		int spare = -1;
		for (int x = 0; x < array.length; x++) {
			if (array[x] == target && c.getItems().isStackable(target)) {
				return x;
			} else if (spare == -1 && array[x] <= 0) {
				spare = x;
			}
		}
		return spare;
	}


	//Reset price checker
	public static void clearConfig(Player c) {
		for (int x = 0; x < c.price.length; x++) {
			if (c.price[x] > 0) {
				c.getItems().addItem(c.price[x], c.priceN[x]);
				c.price[x] = 0;
				c.priceN[x] = 0;
			}
		}
		c.isChecking = false;
		c.getItems().updateInventory = true;
		c.getItems().resetItems(5064);
	}

	//Put item on interface
	public static void depositItem(Player c, int id, int amount) {
		int slot = arraySlot(c, c.price, id);

		//Check if item is untradeable
		if (!c.getItems().isTradeable(id)){
			return;
		}

		//Check amount of certain item the player has
		if (c.getItems().getItemAmount(id) < amount) {
			amount = c.getItems().getItemAmount(id);
		}

		//Check if price checker is full
		if (slot == -1) {
			c.sendMessage("The price checker is currently full.");
			return;
		}

		//If item isn't stackable, put one at a time
//		if (!c.getItems().isStackable(id)) {
//			amount = 1;
//		}

		//If player doesn't have that certain item, return

		if (!c.getItems().playerHasItem(id, amount)) {
			return;
		}



		//Add item to interface
		if (c.getItems().isStackable(id)) {
			c.getItems().deleteItem2(id, amount);
			if (c.price[slot] != id) {
				c.price[slot] = id;
				c.priceN[slot] = amount;
			} else {
				c.price[slot] = id;
				c.priceN[slot] += amount;
			}
		} else {
			for (int i = 0; i < amount; i++) {
				if (slot == -1) {
					break;
				}
				c.getItems().deleteItem2(id, 1);
				c.price[slot] = id;
				c.priceN[slot] = 1;
				slot = arraySlot(c, c.price, id);
			}
		}

		//Add new item price to total
		c.total += ItemDefinition.forId(id).getGeneralPrice() * amount;
		updateChecker(c);
	}

	//Show item in the price checker interface
	public static void itemOnInterface(Player c, int frame, int slot, int id,
									   int amount) {
		c.outStream.createFrameVarSizeWord(34);
		c.outStream.writeWord(frame);
		c.outStream.writeByte(slot);
		c.outStream.writeWord(id + 1);
		c.outStream.writeByte(255);
		c.outStream.writeDWord(amount);
		c.outStream.endFrameVarSizeWord();
	}

	//Open the interface
	public static void open(Player c) {
		c.isChecking = true; //Player is checking Price Checker
		c.total = 0; //Initial total price is 0
		c.getPA().sendFrame126("" + Misc.insertCommas(Integer.toString(c.total)) + "", 7351); //Show total price
		c.getPA().sendFrame126("Click on items in your inventory to check their values", 7352);
		updateChecker(c);//Update the price checker
		resetFrames(c);
		c.getItems().resetItems(5064);
		c.getPA().sendFrame248(43933, 5063);
	}


	public static void resetFrames(Player c) {
		for (int x = 0; x < capacity; x++) {
			if (c.price[x] <= 1) {
				setFrame(c, x, getFramesForSlot[x][1], c.price[x], c.priceN[x], false);
			}
		}
	}

	private static void setFrame(Player player, int slotId, int frameId, int itemId, int amount, boolean store) {
		int totalAmount = ItemDefinition.forId(itemId).getGeneralPrice() * amount;
		String total = Misc.insertCommas(Integer.toString(totalAmount));
		if (!store) {
			player.getPA().sendFrame126("", frameId);
			player.getPA().sendFrame126("", frameId + 1);
			player.getPA().sendFrame126("", frameId + 2);
			return;
		}
		if (player.getItems().isStackable(itemId)) {
			player.getPA().sendFrame126("" + amount + " x", frameId);
			player.getPA().sendFrame126("" + Misc.insertCommas(Integer.toString(ItemDefinition.forId(itemId).getGeneralPrice()) + " ="), frameId + 1);
			player.getPA().sendFrame126("" + total + "", frameId + 2);
		} else {
			player.getPA().sendFrame126("" + Misc.insertCommas(Integer.toString(ItemDefinition.forId(itemId).getGeneralPrice()) + ""), frameId);
			player.getPA().sendFrame126("", frameId + 1);
			player.getPA().sendFrame126("", frameId + 2);
		}
	}

	//Update price checker
	public static void updateChecker(Player c) {
		c.getItems().resetItems(5064);

		for (int x = 0; x < capacity; x++) {
			if (c.priceN[x] <= 0) {
				itemOnInterface(c, 7246, x, -1, 0);
			} else {
				itemOnInterface(c, 7246, x, c.price[x], c.priceN[x]);
				c.getPA().sendFrame126("", 7352);
				for (int frames = 0; frames < getFramesForSlot.length; frames++) {
					if (x == getFramesForSlot[frames][0]) {
						setFrame(c, x, getFramesForSlot[frames][1], c.price[x], c.priceN[x], true);
					}
				}
			}
		}

		c.getPA().sendFrame126("" + Misc.insertCommas(Integer.toString(c.total < 0 ? 0 : c.total)) + "", 7351);
	}

	public static void updateSlot(Player c, int slot) {
		for (int frames = 0; frames < getFramesForSlot.length; frames++) {
			if (slot == getFramesForSlot[frames][0]) {
				if (c.priceN[slot] >= 1) {
					setFrame(c, slot, getFramesForSlot[frames][1], c.price[slot], c.priceN[slot], true);
				} else {
					setFrame(c, slot, getFramesForSlot[frames][1], c.price[slot], c.priceN[slot], false);
				}
			}
		}
	}

	public static void withdrawItem(Player c, int removeId, int slot, int amount) {
		int amount2 = 0;

		if (!c.isChecking) {
			return;
		}

		if (c.price[slot] != removeId) {
			return;
		}
//
//		if (!c.getItems().isStackable(c.price[slot])) {
//			amount = 1;
//		}

		for (int i = 0; i < c.price.length; i++) {
			if (c.price[i] == removeId) {
				amount2++;
			}
		}

		if (c.getItems().isStackable(c.price[slot])){
			if (amount > c.priceN[slot]) {
				amount = c.priceN[slot];
			}
		} else {
			if (amount > amount2) {
				amount = amount2;
			}
		}

		if (c.getRights().getValue() == 3) {
			c.sendMessage("DEBUGGING:");
			c.sendMessage("" + amount + " " + amount2);
		}

		if (c.getItems().isStackable(removeId)){
			if (c.price[slot] >= 0 && c.getItems().freeSlots() > 0) {
				c.getItems().addItem(c.price[slot], amount);
				if (c.getItems().playerHasItem(c.price[slot], amount)) {
					c.priceN[slot] -= amount;
					c.price[slot] = c.priceN[slot] <= 0 ? 0 : c.price[slot];
				} else {
					c.priceN[slot] = 0;
					c.price[slot] = 0;
				}
				updateSlot(c, slot);
			}
		} else {
			int missing = amount;
			for (int i = 0; i < c.price.length; i++){
				if (missing > 0) {
					if (c.price[i] == removeId){
						missing--;
						c.priceN[i] = 0;
						c.price[i] = 0;
						updateSlot(c, i);
						c.getItems().addItem(removeId, 1);
					}
				}
			}
		}

		c.total -= ItemDefinition.forId(removeId).getGeneralPrice() * amount;

		updateChecker(c);
	}

	public static void withdrawAllItem(Player c, int removeId, int slot) {
		int amount = 0;

		if (!c.isChecking) {
			return;
		}

		if (c.price[slot] != removeId) {
			return;
		}
//
//		if (!c.getItems().isStackable(c.price[slot])) {
//			amount = 1;
//		}


		if (c.getItems().isStackable(c.price[slot])){
			amount = c.priceN[slot];
		} else {
			for (int i = 0; i < c.price.length; i++) {
				if (c.price[i] == removeId) {
					amount++;
				}
			}
		}

		if (c.getItems().isStackable(removeId)){
			if (c.price[slot] >= 0 && c.getItems().freeSlots() > 0) {
				c.getItems().addItem(c.price[slot], amount);
				if (c.getItems().playerHasItem(c.price[slot], amount)) {
					c.priceN[slot] -= amount;
					c.price[slot] = c.priceN[slot] <= 0 ? 0 : c.price[slot];
				} else {
					c.priceN[slot] = 0;
					c.price[slot] = 0;
				}
				updateSlot(c, slot);
			}
		} else {
			int missing = amount;
			for (int i = 0; i < c.price.length; i++){
				if (missing > 0) {
					if (c.price[i] == removeId){
						missing--;
						c.priceN[i] = 0;
						c.price[i] = 0;
						updateSlot(c, i);
						c.getItems().addItem(removeId, 1);
					}
				}
			}
		}

		c.total -= ItemDefinition.forId(removeId).getGeneralPrice() * amount;

		updateChecker(c);
	}

}

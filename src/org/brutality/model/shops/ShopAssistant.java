package org.brutality.model.shops;

import org.brutality.Config;
import org.brutality.Server;
import org.brutality.model.content.dialogue.impl.ConverterDialogue;
import org.brutality.model.items.ItemDefinition;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.util.Misc;
import org.brutality.world.ShopHandler;

public class ShopAssistant {

	private Player c;

	public ShopAssistant(Player client) {
		this.c = client;
	}

	public boolean shopSellsItem(int itemID) {
		for (int i = 0; i < ShopHandler.ShopItems[c.myShopId].length; i++) {
			if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
				return true;
			}
		}
		return false;
	}

	public static final int[][] PKP_DATA = { { 1050, 100000000 } };

	/**
	 * Shops
	 **/

	/*
	public void showCurrency(int ShopID){
		c.getPA().sendFrame171(1, 28050);
		String title = "";
		title = title + ShopHandler.ShopName[ShopID];
		switch (ShopID){
			case 80:
				title = title  + " - " + "Bounties: " + Misc.insertCommas(Integer.toString(c.getBH().getBounties()));
				break;
			case 44:
			case 10:
				title = title + " - " + "Slayer Points: " + Misc.insertCommas(Integer.toString(c.slayerPoints));
				break;
			case 12:
			case 49:
			case 50:
				title = title + " - " + "PK Points: " + Misc.insertCommas(Integer.toString(c.pkp));
				break;
			case 77:
			case 115:
				title = title + " - " + "Vote Points: " + Misc.insertCommas(Integer.toString(c.votePoints));
				break;
			case 9 :
			case 116:
			case 117:
			case 71:
				title = title + " - " + "Donator Points: " + Misc.insertCommas(Integer.toString(c.donatorPoints));
				break;
			case 78:
				title = title + " - " + "Achievement Points: " + Misc.insertCommas(Integer.toString(c.achievementPoints));
				break;
			default:
				title = title;
				break;
		}
		c.getPA().sendFrame126(title, 3901);
	}
	*/

	public void showCurrency(int ShopID) {
		switch(ShopID) {
			//Bounties
			case 80:
				c.getPA().sendFrame171(0, 28050);
				c.getPA().sendChangeSprite(28051, (byte) 7);
				c.getPA().sendFrame126("Bounties: " + Misc.insertCommas(Integer.toString(c.getBH().getBounties())), 28052);
				break;
			//Slayer Points
			case 44:
			case 10:
				c.getPA().sendFrame171(0, 28050);
				c.getPA().sendChangeSprite(28051, (byte) 1);
				c.getPA().sendFrame126("Slayer Points: " + Misc.insertCommas(Integer.toString(c.slayerPoints)), 28052);
				break;
			//PK Points
			case 12:
			case 49:
			case 50:
				c.getPA().sendFrame171(0, 28050);
				c.getPA().sendChangeSprite(28051, (byte) 3);
				c.getPA().sendFrame126("PK Points: " + Misc.insertCommas(Integer.toString(c.pkp)), 28052);
				break;
			//Vote Points
			case 77:
			case 115:
				c.getPA().sendFrame171(0, 28050);
				c.getPA().sendChangeSprite(28051, (byte) 4);
				c.getPA().sendFrame126("Vote Points: " + Misc.insertCommas(Integer.toString(c.votePoints)), 28052);
				break;
			//Donator Points
			case 9 :
			case 116:
			case 117:
			case 71:
				c.getPA().sendFrame171(0, 28050);
				c.getPA().sendChangeSprite(28051, (byte) 6);
				c.getPA().sendFrame126("Donator Points: " + Misc.insertCommas(Integer.toString(c.donatorPoints)), 28052);
				break;
			//Achievement Points
			case 78:
				c.getPA().sendFrame171(0, 28050);
				c.getPA().sendChangeSprite(28051, (byte) 5);
				c.getPA().sendFrame126("Achievement Points: " + Misc.insertCommas(Integer.toString(c.achievementPoints)), 28052);
				break;
			case 200:
				c.getPA().sendFrame171(0, 28050);
				c.getPA().sendChangeSprite(28051, (byte) 8);
				c.getPA().sendFrame126("Marks of Grace: " + Misc.insertCommas(Integer.toString(c.achievementPoints)), 28052);
				break;
			default:
				c.getPA().sendFrame171(0, 28050);
				c.getPA().sendChangeSprite(28051, (byte) 2);
				c.getPA().sendFrame126("Coins: " + Misc.insertCommas(Integer.toString(c.getItems().getItemAmount(995))), 28052);
				break;
		}
	}


	public void openShop(int ShopID) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		c.getItems().resetItems(3823);
		resetShop(ShopID);
		c.isShopping = true;
		c.myShopId = ShopID;
		c.getPA().sendFrame248(3824, 3822);
		// Replace with sprite + currency: #### (showCurrency function)
		/*
		if (ShopID == 68)
			c.getPA().sendFrame126("Fishing Tourney Shop - Points: " + c.fishingTourneyPoints, 3901);
		else if (ShopID == 72)
			c.getPA().sendFrame126("Blast Mine Shop - Points: " + c.blastPoints, 3901);
		else if (ShopID == 74)
			c.getPA().sendFrame126("Sacrifice Shop - Points: " + c.hungerPoints, 3901);
		else if (ShopID == 80)
			c.getPA().sendFrame126("Bounty Shop - Bounties: " + c.getBH().getBounties(), 3901);
		else
		*/
		c.getPA().sendFrame126(ShopHandler.ShopName[ShopID], 3901);
	}

	public void updatePlayerShop() {
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].isShopping == true && PlayerHandler.players[i].myShopId == c.myShopId
						&& i != c.index) {
					PlayerHandler.players[i].updateShop = true;
				}
			}
		}
	}

	public void updateshop(int i) {
		resetShop(i);
	}

	public void resetShop(int ShopID) {
		int TotalItems = 0;
		for (int i = 0; i < ShopHandler.MaxShopItems - 1; i++) {
			if (ShopHandler.ShopItems[ShopID][i] > 0) {
				TotalItems++;
			}
		}
		if (TotalItems > ShopHandler.MaxShopItems) {
			TotalItems = ShopHandler.MaxShopItems;
		}
		showCurrency(ShopID);
		c.getOutStream().createFrameVarSizeWord(53);
		c.getOutStream().writeWord(3900);
		c.getOutStream().writeWord(TotalItems);
		int TotalCount = 0;
		for (int i = 0; i < ShopHandler.ShopItems[ShopID].length; i++) {
			if (ShopHandler.ShopItems[ShopID][i] > 0 || i <= ShopHandler.ShopItemsStandard[ShopID]) {
				if (ShopHandler.ShopItemsN[ShopID][i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(ShopHandler.ShopItemsN[ShopID][i]);
				} else {
					c.getOutStream().writeByte(ShopHandler.ShopItemsN[ShopID][i]);
				}
				if (ShopHandler.ShopItems[ShopID][i] > Config.ITEM_LIMIT || ShopHandler.ShopItems[ShopID][i] < 0) {
					ShopHandler.ShopItems[ShopID][i] = Config.ITEM_LIMIT;
				}
				c.getOutStream().writeWordBigEndianA(ShopHandler.ShopItems[ShopID][i]);
				TotalCount++;
			}
			if (TotalCount > TotalItems) {
				break;
			}
		}
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
	}

	public double getItemShopValue(int ItemID) {
		double ShopValue = 1;
		double TotPrice = 0;
		// int price = getSpecialItemValue(ItemID);
		int price = ItemDefinition.forId(ItemID).getGeneralPrice();
		ShopValue = price;
		if (ShopValue < 1) {
			ShopValue = price + 1;
		}
		TotPrice = ShopValue;
		return TotPrice;
	}

	public double getItemSpecialValue(int ItemID) {
		double ShopValue = 1;
		double TotPrice = 0;
		int price = ItemDefinition.forId(ItemID).getSpecialPrice();
		// int price = ItemDefinition.forId(ItemID).getGeneralPrice();
		ShopValue = price;
		if (ShopValue < 1) {
			ShopValue = price + 1;
		}
		TotPrice = ShopValue;
		return TotPrice;
	}

	/**
	 * buy item from shop (Shop Price)
	 **/

	public void buyFromShopPrice(int removeId, int removeSlot) {
		int ShopValue = (int) Math.floor(getItemShopValue(removeId));
		ShopValue *= 1.00;
		String ShopAdd = "";
		if(c.myShopId == 12 || c.myShopId == 49 || c.myShopId == 50) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getPKPValue(removeId)
					+ " PK points.");
			return;
		}
		if (c.myShopId == 68) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getFishingTourneyValue(removeId)
					+ " Fishing Tourney points.");
			return;
		}
		if (c.myShopId == 72) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getBlastValue(removeId)
					+ " Blast points.");
			return;
		}
		if (c.myShopId == 44 || c.myShopId == 10) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getSlayerValue(removeId)
					+ " Slayer points.");
			return;
		}
		if (c.myShopId == 80) {
			c.sendMessage(c.getItems().getItemName(removeId) + " currently costs " + Misc.insertCommas(Integer.toString(getBountiesValue(removeId)))
					+ " Bounties.");
			return;
		}
		if (c.myShopId == 77 || c.myShopId == 115) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getVoteValue(removeId)
					+ " Vote points.");
			return;
		}
		if (c.myShopId == 78) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getAchievementsValue(removeId)
					+ " Achievement points.");
			return;
		}
		if (c.myShopId == 75) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getPCValue(removeId)
					+ " PC points.");
			return;
		}
		if (c.myShopId == 9 || c.myShopId == 116 || c.myShopId == 117 || c.myShopId == 71) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getDonatorValue(removeId)
					+ " Donator points.");
			return;
		}
		if (c.myShopId == 29) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getTokkulValue(removeId)
					+ " Tokkul.");
			return;
		}
		if (c.myShopId == 200) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getGraceValue(removeId)
					+ " Marks of Grace.");
			return;
		}
		if (c.myShopId == 210) {
			c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs " + getRedwoodValue(removeId)
					+ " Redwood logs.");
			return;
		}
		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + (ShopValue / 1000) + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + (ShopValue / 1000000) + " million)";
		}
		c.sendMessage(
				c.getItems().getItemName(removeId) + ": currently costs " + ShopValue + " Coins." + ShopAdd);
	}

	/**
	 * Sell item to shop (Shop Price)
	 **/
	public void sellToShopPrice(int removeId, int removeSlot) {
		for (int i : Config.ITEMS_KEPT_ON_DEATH) {
			if (i == removeId) {
				c.sendMessage("You can't sell " + c.getItems().getItemName(removeId).toLowerCase() + ".");
				return;
			}
		}

		if (c.myShopId == 12 || c.myShopId == 49 || c.myShopId == 50 // PK Points
				|| c.myShopId == 60	// Fishing Tourney points
				|| c.myShopId == 74 // Sacrifice points
				|| c.myShopId == 72	// Blast points
				|| c.myShopId == 44 || c.myShopId == 10 // Slayer points
				|| c.myShopId == 80 // Bounties
				|| c.myShopId == 77 || c.myShopId == 115 // Vote points
				|| c.myShopId == 78 // Achievement points
				|| c.myShopId == 75 // PC points
				|| c.myShopId == 9 || c.myShopId == 116 || c.myShopId == 117 || c.myShopId == 71 // Donator points
		//		|| c.myShopId == 29 // Tokkul
				|| c.myShopId == 99 // Valuable blood
				|| c.myShopId == 200 // Marks of Grace
				|| c.myShopId == 210) // Redwood logs
		{
			c.sendMessage("You can't sell " + c.getItems().getItemName(removeId).toLowerCase() + " to this store.");
			return;
		}

		boolean IsIn = false;
		if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
			for (int j = 0; j <= ShopHandler.ShopItemsStandard[c.myShopId]; j++) {
				if (removeId == (ShopHandler.ShopItems[c.myShopId][j] - 1)) {
					IsIn = true;
					break;
				}
			}
		} else {
			IsIn = true;
		}
		if (IsIn == false) {
			c.sendMessage("You can't sell " + c.getItems().getItemName(removeId).toLowerCase() + " to this store.");
		} else {
			int ShopValue = (int) Math.floor(getItemShopValue(removeId));

			if (c.myShopId == 29) {
				c.sendMessage(
						c.getItems().getItemName(removeId) + ": shop will buy for " + getTokkulValue(removeId) + " Tokkul" + formattedPrice(ShopValue));
			} else {
				ShopValue = (int) Math.floor(ShopValue * 0.75);
				c.sendMessage(
						c.getItems().getItemName(removeId) + ": shop will buy for " + ShopValue + " Coins" + formattedPrice(ShopValue));
			}
		}
	}

	public String formattedPrice(int price){
		String ShopAdd = "";
		if (price >= 1000 && price < 1000000) {
			ShopAdd = " (" + (price / 1000) + "K)";
		} else if (price >= 1000000 && price < 1000000000) {
			ShopAdd = " (" + (price / 1000000) + "M)";
		} else if (price >= 1000000000) {
			ShopAdd = " (" + (price / 1000000000) + "B";
		}
		return ShopAdd;
	}

	@SuppressWarnings("unused")
	public boolean sellItem(int itemID, int fromSlot, int amount) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return false;
		}

		//Anti cheat engine
		if (!c.getItems().playerHasItem(itemID)){
			return false;
		}

		if (amount > c.getItems().getItemAmount(itemID)){
			amount = c.getItems().getItemAmount(itemID);
		}

		if (!c.getItems().playerHasItem(itemID, amount)){
			return false;
		}

		// if (c.myShopId == 22 || c.myShopId == 49 || c.myShopId == 116 ||
		// c.myShopId == 9 || c.myShopId == 50 || c.myShopId == 12 || c.myShopId
		// == 20 || c.myShopId == 2 || c.myShopId == 88 || c.myShopId == 111 ||
		// c.myShopId == 113 || c.myShopId == 3
		// || c.myShopId == 4 || c.myShopId == 5 || c.myShopId == 16) {
		// c.sendMessage("@red@You can't sell items to this shop!");
		// return false;
		// }
		for (int i : Config.ITEMS_KEPT_ON_DEATH) {
			if (i == itemID) {
				c.sendMessage("You can't sell " + c.getItems().getItemName(itemID).toLowerCase() + ".");
				return false;
			}
		}

		if (c.myShopId == 12 || c.myShopId == 49 || c.myShopId == 50 // PK Points
				|| c.myShopId == 60	// Fishing Tourney points
				|| c.myShopId == 74 // Sacrifice points
				|| c.myShopId == 72	// Blast points
				|| c.myShopId == 44 || c.myShopId == 10 // Slayer points
				|| c.myShopId == 80 // Bounties
				|| c.myShopId == 77 || c.myShopId == 115 // Vote points
				|| c.myShopId == 78 // Achievement points
				|| c.myShopId == 75 // PC points
				|| c.myShopId == 9 || c.myShopId == 116 || c.myShopId == 117 || c.myShopId == 71 // Donator points
				//		|| c.myShopId == 29 // Tokkul
				|| c.myShopId == 99 // Valuable blood
				|| c.myShopId == 200 // Marks of Grace
				|| c.myShopId == 210) // Redwood logs
		{
			c.sendMessage("You can't sell " + c.getItems().getItemName(itemID).toLowerCase() + " to this store.");
			return false;
		}

		// if (c.myShopId == 115 || c.myShopId == 77 || c.myShopId == 14||
		// c.myShopId == 116 || c.myShopId == 117 || c.myShopId == 71 ||
		// c.myShopId == 78
		// || c.myShopId == 80 || c.myShopId == 44 || c.myShopId == 22 ||
		// c.myShopId == 66 || c.myShopId == 67
		// || c.myShopId == 56 || c.myShopId == 20 || c.myShopId == 210 ||
		// c.myShopId == 200 || c.myShopId == 68 || c.myShopId == 74 ||
		// c.myShopId == 72
		// || c.myShopId == 70 || c.myShopId == 69 || c.myShopId == 88) {
		// c.sendMessage("@red@You can't sell items to this shop!");
		// return false;
		// }
		if (c.getRights().isAdministrator() && !Config.ADMIN_CAN_SELL_ITEMS) {
			c.sendMessage("Selling items as an admin has been disabled.");
			return false;
		}
		if (amount > 0 && itemID == (c.playerItems[fromSlot] - 1)) {
			if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
				boolean IsIn = false;
				for (int i = 0; i <= ShopHandler.ShopItemsStandard[c.myShopId]; i++) {
					if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
						IsIn = true;
						break;
					}
				}
				if (IsIn == false) {
					c.sendMessage(
							"You can't sell " + c.getItems().getItemName(itemID).toLowerCase() + " to this store.");
					return false;
				}
			}

			/*
			if (amount > c.playerItemsN[fromSlot]
					&& (ItemDefinition.forId((c.playerItems[fromSlot] - 1)).isNoted()
					|| ItemDefinition.forId((c.playerItems[fromSlot] - 1)).isStackable())) {
				amount = c.playerItemsN[fromSlot];
			} else if (amount > c.getItems().getItemAmount(itemID)
					&& !ItemDefinition.forId((c.playerItems[fromSlot] - 1)).isNoted()
					&& !ItemDefinition.forId((c.playerItems[fromSlot] - 1)).isStackable()) {
				amount = c.getItems().getItemAmount(itemID);
			}*/

			int TotPrice2 = 0;
			if (c.myShopId == 29) {
				TotPrice2 = (int) Math.floor(getTokkulValue(itemID) * amount);
			} else {
				TotPrice2 = (int) Math.floor(getItemShopValue(itemID) * amount);
			}
			int TotPrice3 = 0;
			TotPrice3 = (int) Math.floor(TotPrice2 * 0.75);

			int currency = c.myShopId == 29 ? 6529 : 995;

			if (exceedsMaxItemAmount(currency, amount*TotPrice3)){
				c.sendMessage("You're already holding the maximum amount of " + ItemDefinition.forId(currency).getName().toLowerCase() + " possible.");
				return false;
			}

			if (c.getItems().freeSlots() > 0 || c.getItems().playerHasItem(995)) {
				c.getItems().deleteItem2(itemID, amount);
				c.getItems().addItem(currency, TotPrice3);
				addShopItem(itemID, amount);
			}

			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return true;
	}

	public boolean addShopItem(int itemID, int amount) {
		boolean Added = false;
		if (amount <= 0) {
			return false;
		}
		for (int i = 0; i < ShopHandler.ShopItems[c.myShopId].length; i++) {
			if ((ShopHandler.ShopItems[c.myShopId][i] - 1) == itemID) {
				ShopHandler.ShopItemsN[c.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < ShopHandler.ShopItems[c.myShopId].length; i++) {
				if (ShopHandler.ShopItems[c.myShopId][i] == 0) {
					ShopHandler.ShopItems[c.myShopId][i] = (itemID + 1);
					ShopHandler.ShopItemsN[c.myShopId][i] = amount;
					ShopHandler.ShopItemsDelay[c.myShopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}

	public boolean buyItem(int itemID, int fromSlot, int amount) {
		if (Server.getMultiplayerSessionListener().inAnySession(c))
			return false;
		if (c.myShopId == 14) {
			skillBuy(itemID);
			return false;
		}
		//Anti cheat engine
		if (!shopSellsItem(itemID)) {
			return false;
		}
		if (c.myShopId == 15) {
			buyVoid(itemID);
			return false;
		}
		if (amount > 0) {
			if (amount > ShopHandler.ShopItemsN[c.myShopId][fromSlot]) {
				amount = ShopHandler.ShopItemsN[c.myShopId][fromSlot];
			}

			// double ShopValue;
			// double TotPrice;
			int TotPrice2 = 0;
			// int Overstock;
			int Slot = 0; // coins
			int Slot1 = 0; // tokkul
			int Slot2 = 0; // blood
			int Slot3 = 0; // grace
			int Slot4 = 0; // redwood
			if (c.myShopId == 80) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 12 || c.myShopId == 49 || c.myShopId == 50 || c.myShopId == 56) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 44 || c.myShopId == 68 || c.myShopId == 74 || c.myShopId == 72) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 79) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 9) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 116 || c.myShopId == 117) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 71) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 10) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 77) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 115) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 78) {
				handleOtherShop(itemID, amount);
				return false;
			}
			if (c.myShopId == 75) {
				handleOtherShop(itemID, amount);
				return false;
			}
			//for (int i = amount; i > 0; i--) {
				TotPrice2 = (int) Math.floor(getItemShopValue(itemID) * amount);
				Slot = c.getItems().getItemSlot(995);
				Slot1 = c.getItems().getItemSlot(6529);
				Slot2 = c.getItems().getItemSlot(8125);
				Slot3 = c.getItems().getItemSlot(11849);
				Slot4 = c.getItems().getItemSlot(19670);
				if (Slot1 == -1 && (c.myShopId == 29)) {
					c.sendMessage("You don't have enough Tokkul.");
					return false;
				}
				if (Slot2 == -1 && (c.myShopId == 99)) {
					c.sendMessage("You don't have enough Valuable blood.");
					return false;
				}
				if (Slot3 == -1 && (c.myShopId == 200)) {
					c.sendMessage("You don't have enough Marks of Grace.");
					return false;
				}
				if (Slot4 == -1 && (c.myShopId == 210)) {
					c.sendMessage("You don't have enough Redwood logs.");
					return false;
				}
				if (Slot == -1) {
					c.sendMessage("You don't have enough Coins.");
					return false;
				}

				if (exceedsMaxItemAmount(itemID, amount)){
					c.sendMessage("You're already holding the maximum amount of " + ItemDefinition.forId(itemID).getName().toLowerCase() + " possible.");
					return false;
				}

				if (c.myShopId != 29 && c.myShopId != 99 && c.myShopId != 200 && c.myShopId != 210) {
					if (c.playerItemsN[Slot] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
									TotPrice2);
							c.getItems().addItem(itemID, amount);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= amount;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if (c.myShopId == 26 && ShopHandler.ShopItemsN[c.myShopId][fromSlot] == 0) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							return false;
						}
					} else {
						c.sendMessage("You don't have enough Coins.");
						return false;
					}
				}
				if (c.myShopId == 29) {
					if (c.playerItemsN[Slot1] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(6529, c.getItems().getItemSlot(6529), TotPrice2);
							c.getItems().addItem(itemID, amount);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							return false;
						}
					} else {
						c.sendMessage("You don't have enough Tokkul.");
						return false;
					}
				}
				if (c.myShopId == 200) {
					if (c.playerItemsN[Slot3] >= getGraceValue(itemID)) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(11849, c.getItems().getItemSlot(11849), getGraceValue(itemID));
							c.getItems().addItem(itemID, amount);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							return false;
						}
					} else {
						c.sendMessage("You don't have enough Marks of Grace.");
						return false;
					}
				}
				if (c.myShopId == 210) {
					if (c.playerItemsN[Slot4] >= getRedwoodValue(itemID)) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(19670, c.getItems().getItemSlot(19670), getRedwoodValue(itemID));
							c.getItems().addItem(itemID, amount);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							return false;
						}
					} else {
						c.sendMessage("You don't have enough Redwood logs.");
						return false;
					}
				}
				if (c.myShopId == 99) {
					if (c.playerItemsN[Slot2] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(8125, c.getItems().getItemSlot(8125), TotPrice2);
							c.getItems().addItem(itemID, amount);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							return false;
						}
					} else {
						c.sendMessage("You don't have enough Valuable blood.");
						return false;
					}
				}
			//}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return false;
	}

	public boolean exceedsMaxItemAmount(int itemID, int amount) {
		long totalAmount = ((long) c.getItems().getItemAmount(itemID) + (long) amount);
		if (totalAmount >= Integer.MAX_VALUE) {
			return true;
		}
		return false;
	}

	public void handleOtherShop(int itemID, int amount) {
		if (!shopSellsItem(itemID)) {
			return;
		}
		if (amount <= 0) {
			c.sendMessage("You need to buy atleast one or more of this item.");
			return;
		}
		if (!c.getItems().isStackable(itemID)) {
			if (amount > c.getItems().freeSlots()) {
				amount = c.getItems().freeSlots();
			}
		}

		if (exceedsMaxItemAmount(itemID, amount)) {
			c.sendMessage("You're already holding the maximum amount of " + ItemDefinition.forId(itemID).getName().toLowerCase() + " possible.");
			return;
		}

		if (c.myShopId == 80) {
			if (c.getItems().freeSlots() < 1) {
				c.sendMessage("You need atleast one free slot to buy this.");
				return;
			}
			int itemValue = getBountiesValue(itemID) * amount;
			if (c.getBH().getBounties() < itemValue) {
				c.sendMessage("You do not have enough Bounties to buy this from the shop.");
				return;
			}
			c.getBH().setBounties(c.getBH().getBounties() - itemValue);
			c.getItems().addItem(itemID, amount);
			c.getItems().resetItems(3823);
			//c.getPA().sendFrame126("Bounties: " + Misc.insertCommas(Integer.toString(c.getBH().getBounties())), 28052);
			return;
		}
		if (c.myShopId == 12 || c.myShopId == 49 || c.myShopId == 50 || c.myShopId == 56) {
			if (c.pkp >= getPKPValue(itemID) * amount) {
				if (c.getItems().freeSlots() > 0) {
					c.pkp -= (getPKPValue(itemID) * amount);
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, amount);
					c.getItems().resetItems(3823);
					//c.getPA().sendFrame126("PKP Shop - Points: " + c.pkp, 3901);
				}
			} else {
				c.sendMessage("You do not have enough PK points to buy this item.");
			}
		} else if (c.myShopId == 68) {
			if (c.fishingTourneyPoints >= getFishingTourneyValue(itemID) * amount) {
				if (c.getItems().freeSlots() > 0) {
					c.fishingTourneyPoints -= getFishingTourneyValue(itemID) * amount;
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, amount);
					c.getItems().resetItems(3823);
					//c.getPA().sendFrame126("Fishing Tourney Shop - Points: " + c.fishingTourneyPoints, 3901);
				}
			} else {
				c.sendMessage("You do not have enough Fishing Tourney points to buy this item.");
			}
		} else if (c.myShopId == 74) {
			if (c.hungerPoints >= getHungerValue(itemID) * amount) {
				if (c.getItems().freeSlots() > 0) {
					c.hungerPoints -= getHungerValue(itemID) * amount;
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, amount);
					c.getItems().resetItems(3823);
					//c.getPA().sendFrame126("Sacrifice Shop - Points: " + c.hungerPoints, 3901);
				}
			} else {
				c.sendMessage("You do not have enough Sacrifice points to buy this item.");
			}
		} else if (c.myShopId == 72) {
			if (c.blastPoints >= getBlastValue(itemID) * amount) {
				if (c.getItems().freeSlots() > 0) {
					c.blastPoints -= getBlastValue(itemID) * amount;
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, amount);
					c.getItems().resetItems(3823);
					//c.getPA().sendFrame126("Blast Mine Shop - Points: " + c.blastPoints, 3901);
				}
			} else {
				c.sendMessage("You do not have enough Blast points to buy this item.");
			}
		} else if (c.myShopId == 44 || c.myShopId == 10) {
			if (c.slayerPoints >= getSlayerValue(itemID) * amount) {
				if (c.getItems().freeSlots() > 0) {
					c.slayerPoints -= getSlayerValue(itemID) * amount;
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, amount);
					c.getItems().resetItems(3823);
					//c.getPA().sendFrame126("@whi@Slayer Points: @gre@" + c.slayerPoints + " ", 7333);
				}
			} else {
				c.sendMessage("You do not have enough Slayer points to buy this item.");
			}
		} else if (c.myShopId == 71 || c.myShopId == 116 || c.myShopId == 117 || c.myShopId == 9) {
			if (c.donatorPoints >= getDonatorValue(itemID) * amount) {
				if (c.getItems().freeSlots() > 0) {
					c.donatorPoints -= getDonatorValue(itemID) * amount;
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, amount);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Donator points to buy this item.");
			}
		} else if (c.myShopId == 78) {
			if (c.getAchievements().points >= getAchievementsValue(itemID) * amount) {
				if (c.getItems().freeSlots() > 0) {
					c.getAchievements().points -= getAchievementsValue(itemID) * amount;
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, amount);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Achievement points to buy this item.");
			}
		} else if (c.myShopId == 75) {
			if (c.pcPoints >= getPCValue(itemID) * amount) {
				if (c.getItems().freeSlots() > 0) {
					c.pcPoints -= getPCValue(itemID) * amount;
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, amount);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough PC points to buy this item.");
			}
		} else if (c.myShopId == 115 || c.myShopId == 77) {
			if (c.votePoints >= getVoteValue(itemID) * amount) {
				if (c.getItems().freeSlots() > 0) {
					c.votePoints -= getVoteValue(itemID) * amount;
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, amount);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Vote points to buy this item.");
			}
		}
		showCurrency(c.myShopId);
	}

	public void openSkillCape() {
		int capes = get99Count();
		if (capes > 1)
			capes = 1;
		else
			capes = 0;
		c.myShopId = 14;
		setupSkillCapes(capes, get99Count());
	}

	/*
	 * public int[][] skillCapes =
	 * {{0,9747,4319,2679},{1,2683,4329,2685},{2,2680
	 * ,4359,2682},{3,2701,4341,2703
	 * },{4,2686,4351,2688},{5,2689,4347,2691},{6,2692,4343,2691},
	 * {7,2737,4325,2733
	 * },{8,2734,4353,2736},{9,2716,4337,2718},{10,2728,4335,2730
	 * },{11,2695,4321,2697},{12,2713,4327,2715},{13,2725,4357,2727},
	 * {14,2722,4345
	 * ,2724},{15,2707,4339,2709},{16,2704,4317,2706},{17,2710,4361,
	 * 2712},{18,2719,4355,2721},{19,2737,4331,2739},{20,2698,4333,2700}};
	 */
	public int[] skillCapes = { 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795,
			9792, 9774, 9771, 9777, 9786, 9810, 9765, 9789, 9948 };

	public int get99Count() {
		int count = 0;
		for (int j = 0; j < c.playerLevel.length; j++) {
			if (Player.getLevelForXP(c.playerXP[j]) >= 99) {
				count++;
			}
		}
		return count;
	}

	public void setupSkillCapes(int capes, int capes2) {
		c.getPA().sendFrame171(1, 28050);
		c.getItems().resetItems(3823);
		c.isShopping = true;
		c.myShopId = 14;
		c.getPA().sendFrame248(3824, 3822);
		c.getPA().sendFrame126("Skillcape Shop", 3901);

		int TotalItems = 0;
		TotalItems = capes2;
		if (TotalItems > ShopHandler.MaxShopItems) {
			TotalItems = ShopHandler.MaxShopItems;
		}
		c.getOutStream().createFrameVarSizeWord(53);
		c.getOutStream().writeWord(3900);
		c.getOutStream().writeWord(TotalItems);
		for (int i = 0; i <= 22; i++) {
			if (Player.getLevelForXP(c.playerXP[i]) < 99)
				continue;
			if (skillCapes[i] == -1)
				continue;
			c.getOutStream().writeByte(1);
			c.getOutStream().writeWordBigEndianA(skillCapes[i] + 2);
		}
		//Cheap hax fix
		c.getOutStream().writeByte(1);
		c.getOutStream().writeWordBigEndianA(0);
		c.getOutStream().writeByte(1);
		c.getOutStream().writeWordBigEndianA(0);
		//End of cheap hax fix
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
		// }
	}

	public void skillBuy(int item) {
		int nn = get99Count();
		if (nn > 1)
			nn = 1;
		else
			nn = 0;
		for (int j = 0; j < skillCapes.length; j++) {
			if (skillCapes[j] == item || skillCapes[j] + 1 == item) {
				if (c.getItems().freeSlots() > 1) {
					if (c.getItems().playerHasItem(995, 99000)) {
						if (Player.getLevelForXP(c.playerXP[j]) >= 99) {
							c.getItems().deleteItem(995, c.getItems().getItemSlot(995), 99000);
							c.getItems().addItem(skillCapes[j] + nn, 1);
							c.getItems().addItem(skillCapes[j] + 2, 1);
						} else {
							c.sendMessage("You must have 99 in the skill of the cape you're trying to buy.");
						}
					} else {
						c.sendMessage("You need 99,000 coins to buy this item.");
					}
				} else {
					c.sendMessage("You must have at least 1 inventory spaces to buy this item.");
				}
			}
		}
		c.getItems().resetItems(3823);
	}


	/**
	 * Prices for Marks of Grace Shop
	 * @param id
	 * @return
	 */
	private int getGraceValue(int id) {
		switch(id){
			case 11850:
			case 11854:
			case 11856:
			case 11858:
			case 11860:
			case 11852:
				return 25;
			case 11738:
				return 15;
		}
		return 0;
	}

	/**
	 * Prices for Redwood Logs Shop
	 * @param id
	 * @return
	 */
	private int getRedwoodValue(int id) {
		switch(id){
			case 6739:
				return 500;
			case 20011:
				return 1000;
		}
		return 0;
	}

	/**
	 * Prices for Donator Points Shop
	 * @param id
	 * @return
	 */
	public int getDonatorValue(int id) {
		switch (id) {
			case 1038:
			case 1040:
			case 1042:
			case 1044:
			case 1046:
			case 1048:
			case 11862:
			case 11863:
			case 12399:
				return 1199;
			case 1050:
			case 1053:
			case 1055:
			case 1057:
			case 13343:
			case 13344:
				return 799;
			case 6199:
				return 99;
			case 12817:
			case 12825:
			case 12821:
				return 1599;
			case 15001:
				return 1799;
			case 13652:
			case 11802:
				return 1899;
			case 4084:
				return 399;
			case 11832:
				return 899;
			case 11834:
				return 1299;
			case 11283:
			case 11284:
			case 12006:
				return 499;
			case 11785:
			case 11804:
			case 11806:
			case 11808:
			case 11826:
			case 11828:
			case 11830:
				return 899;
			case 12924:
			case 12902:
				return 1499;
			case 11791:
			case 13576:
			case 13265:
			case 13263:
				return 1099;
			case 19481:
				return 1699;
			case 10350:
			case 10348:
			case 10346:
			case 10352:
				return 2199;
			case 15098:
				return 1499;
			case 299:
				return 59;
			case 10342:
			case 10344:
			case 10338:
			case 10340:
				return 1999;
			case 10334:
			case 10330:
			case 10332:
			case 10336:
			case 12424:
			case 12422:
			case 12437:
			case 12426:
				return 1699;
			default:
				return Integer.MAX_VALUE;
		}
	}

	/**
	 * Prices for PKP Points Shop
	 * @param id
	 * @return
	 */
	private int getPKPValue(int id) {
		switch(id){
		/*Melee*/
			case 11802:
				return 1000;
			case 11804:
				return 550;
			case 11806:
				return 450;
			case 11808:
				return 350;
			case 13652:
				return 1100;
			case 13576:
				return 650;
			case 12006:
				return 299;
			case 19553:
				return 399;
			case 6585:
			case 6737:
			case 6735:
				return 15;
			case 12954:
			case 10548:
				return 55;
			case 10551:
				return 75;
			case 6570:
				return 110;
			case 11663:
			case 11664:
			case 11665:
			case 8842:
				return 80;
			case 13072:
			case 13073:
				return 200;
			case 8840:
			case 8839:
				return 110;
			case 12929:
				return 250;
			case 11832:
				return 550;
			case 11834:
				return 850;
			case 13239:
				return 350;
			case 12873: //Guthan set
				return 8;
			case 12875: //Verac set
				return 8;
			case 12877: //Dharok set
				return 15;
			case 12695:
			case 11936: //dark crab
			case 13441: //anglerfish
			case 2996: //pk ticket
				return 1;

			/**Range**/

			case 2581:
				return 75;
			case 2577:
				return 100;
			case 12596:
				return 250;
			case 13237:
				return 350;
			case 19547:
				return 400;
			case 11926:
				return 300;
			case 6733:
				return 15;
			case 11235:
				return 20;
			case 11785:
				return 400;
			case 11826:
			case 11830:
				return 200;
			case 11828:
				return 300;
			case 12883:
				return 10;

			/**Magic**/
			case 11791:
				return 600;
			case 6914:
			case 6889:
				return 15;
			case 12002:
				return 75;
			case 19544:
				return 350;
			case 11924:
				return 300;
			case 6731:
				return 15;
			case 13235:
				return 350;
			case 12881:
				return 15;
		}
		return 50000;
	}

	/**
	 * Prices for Slayer Points Shop
	 * @param id
	 * @return
	 */
	private int getSlayerValue(int id) {
		switch(id){
			case 11865:
				return 150;
			case 12774:
				return 500;
			case 2572:
				return 200;
			case 4081:
				return 150;
			case 4168:
			case 4166:
			case 4551:
			case 4164:
			case 4170:
				return 10;
			case 11738:
				return 15;
			case 13226:
				return 30;

		}
		return 0;
	}

	/**
	 * Prices for Vote Points Shop
	 * @param id
	 * @return
	 */
	private int getVoteValue(int id) {
		switch(id){
		}
		return 0;
	}

	/**
	 * Prices for PC Points Shop
	 * @param id
	 * @return
	 */
	private int getPCValue(int id) {
		switch(id){
		}
		return 0;
	}

	/**
	 * Prices for Achievements Points Shop
	 * @param id
	 * @return
	 */
	private int getAchievementsValue(int id) {
		switch(id){
		}
		return 0;
	}

	/**
	 * Prices for Blast Points Shop
	 * @param id
	 * @return
	 */
	private int getBlastValue(int id) {
		switch(id){
		}
		return 0;
	}

	/**
	 * Prices for Fishing Tourney Points Shop
	 * @param id
	 * @return
	 */
	private int getFishingTourneyValue(int id) {
		switch(id){
		}
		return 0;
	}

	/**
	 * Prices for Hunger Points Shop
	 * @param id
	 * @return
	 */
	private int getHungerValue(int id) {
		switch(id) {
			case 6762:
				return 500;
			case 20035:
			case 20038:
			case 20041:
			case 20044:
			case 20047:
				return 2500;
			case 13072:
			case 13073:
				return 7000;
			case 11663:
			case 11664:
			case 11665:
			case 8842:
				return 5000;
		}
		return 0;
	}

	/**
	 * Prices for Bounties Shop
	 * @param id
	 * @return
	 */
	private int getBountiesValue(int id) {
		switch (id) {
			case 12783:
				return 500_000;
			case 12804:
				return 25_000_000;
			case 12851:
				return 10_000_000;
			case 12855:
			case 12856:
				return 2_500_000;
			case 12833:
				return 50_000_000;
			case 12831:
				return 35_000_000;
			case 12829:
				return 25_000_000;
			case 13652:
				return 125_000_000;

			case 12800:
			case 12802:
				return 350_000;
			case 12786:
				return 100_000;
			case 10926:
				return 2_500;
			case 12846:
				return 8_000_000;
			case 12420:
			case 12421:
			case 12419:
			case 12457:
			case 12458:
			case 12459:
				return 10_000_000;
			case 12757:
			case 12759:
			case 12761:
			case 12763:
			case 12788:
				return 500_000;
			case 12526:
				return 1_500_000;
			case 12773:
				return 15_000_000;
			case 12849:
			case 12798:
				return 250_000;
			case 12608:
			case 12610:
			case 12612:
				return 350_000;
			case 12775:
			case 12776:
			case 12777:
			case 12778:
			case 12779:
			case 12780:
			case 12781:
			case 12782:
				return 5_000;

			default:
				return Integer.MAX_VALUE;
		}
	}

	/**
	 * Prices for Tokkul Shop
	 * @param id
	 * @return
	 */
	private int getTokkulValue(int id) {
		switch (id) {
			default:
				return Integer.MAX_VALUE;
		}
	}



	public void openVoid() {
	}

	public void buyVoid(int item) {
	}

}

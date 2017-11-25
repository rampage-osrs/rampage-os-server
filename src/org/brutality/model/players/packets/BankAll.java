package org.brutality.model.players.packets;
import java.util.Objects;

import org.brutality.Server;
import org.brutality.model.content.PriceChecker;
import org.brutality.model.items.GameItem;
import org.brutality.model.items.ItemDefinition;
import org.brutality.model.items.bank.BankItem;
import org.brutality.model.multiplayer_session.MultiplayerSession;
import org.brutality.model.multiplayer_session.MultiplayerSessionFinalizeType;
import org.brutality.model.multiplayer_session.MultiplayerSessionStage;
import org.brutality.model.multiplayer_session.MultiplayerSessionType;
import org.brutality.model.multiplayer_session.duel.DuelSession;
import org.brutality.model.multiplayer_session.trade.TradeSession;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;

/**
 * Bank All Items
 **/
public class BankAll implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		int removeSlot = packet.getUnsignedShortA();
		int interfaceId = packet.getUnsignedShort();
		int removeId = packet.getUnsignedShortA();
		if (!c.canUsePackets) {
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		switch (interfaceId) {
		//Price Checker
		case 43933:
			PriceChecker.withdrawAllItem(c, removeId, removeSlot);
			break;
		case 3900:
			c.getShops().buyItem(removeId, removeSlot, 10);
			break;

		case 3823:
			c.getShops().sellItem(removeId, removeSlot, 10);
			break;

		case 5064:
			//Price checker
			if (c.isChecking) {
				PriceChecker.depositItem(c, removeId, c.getItems().getItemAmount(removeId));
				return;
			}
			if (c.inTrade) {
				return;
			}
			DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST &&
					duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERACTION) {
				c.sendMessage("You have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.isBanking) {
				c.getItems().addToBank(removeId, c.getItems().getItemAmount(removeId), true);
			}
			break;

		case 5382:
			if (!c.isBanking) {
				return;
			}
			if(c.getBank().getBankSearch().isSearching()) {
        		c.getBank().getBankSearch().removeItem(removeId, c.getBank().getCurrentBankTab().getItemAmount(new BankItem(removeId + 1)));
        		return;
        	}
			c.getItems().removeFromBank(removeId, c.getBank().getCurrentBankTab().getItemAmount(new BankItem(removeId + 1)), true);
			break;

		case 3322:
			MultiplayerSession session = Server.getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession || session instanceof DuelSession) {
				session.addItem(c, new GameItem(removeId, c.getItems().getItemAmount(removeId)));
			}
			break;

		case 3415:
			session = Server.getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession) {
				session.removeItem(c, removeSlot, new GameItem(removeId, Integer.MAX_VALUE));
			}
			break;

		case 6669:
			session = Server.getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof DuelSession) {
				session.removeItem(c, removeSlot, new GameItem(removeId, Integer.MAX_VALUE));
			}
			break;

		case 7295:
			if (ItemDefinition.forId(removeId).isStackable()) {
				c.getItems().addToBank(c.playerItems[removeSlot],
						c.playerItemsN[removeSlot], false);
				c.getItems().resetItems(7423);
			} else {
				c.getItems().addToBank(c.playerItems[removeSlot],
						c.getItems().itemAmount(c.playerItems[removeSlot]), false);
				c.getItems().resetItems(7423);
			}
			break;

		}
	}

}

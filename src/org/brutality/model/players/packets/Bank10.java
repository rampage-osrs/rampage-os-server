package org.brutality.model.players.packets;

import java.util.Objects;

import org.brutality.Server;
import org.brutality.model.content.PriceChecker;
import org.brutality.model.items.GameItem;
import org.brutality.model.multiplayer_session.MultiplayerSession;
import org.brutality.model.multiplayer_session.MultiplayerSessionFinalizeType;
import org.brutality.model.multiplayer_session.MultiplayerSessionStage;
import org.brutality.model.multiplayer_session.MultiplayerSessionType;
import org.brutality.model.multiplayer_session.duel.DuelSession;
import org.brutality.model.multiplayer_session.trade.TradeSession;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.model.players.skills.Smithing;
import org.brutality.model.players.skills.crafting.JewelryMaking;
import org.brutality.net.Packet;
/**
 * Bank 10 Items
 **/
public class Bank10 implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		//int interfaceId = packet.getUnsignedShort();
		//int removeId = packet.getUnsignedShortA();
		//int removeSlot = packet.getUnsignedShortA();
		int interfaceId = packet.getLEShort();
		int removeId = packet.getUnsignedShortA();
		int removeSlot = packet.getUnsignedShortA();
		if (!c.canUsePackets) {
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if(c.getRights().isOwner()) {
			c.sendMessage("InterfaceID: "+interfaceId+"");
		}
		switch (interfaceId) {
		// Price Checker
		case -21603:
			PriceChecker.withdrawItem(c, removeId, removeSlot, 10);
			break;
		case 4233:
		case 4239:
		case 4245:
			JewelryMaking.mouldItem(c, removeId, 10);
			break;
		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			Smithing.readInput(c.playerLevel[Player.playerSmithing],
					Integer.toString(removeId), c, 10);
			break;
		case 3900:
			c.getShops().buyItem(removeId, removeSlot, 5);
			break;

		case 3823:
			c.getShops().sellItem(removeId, removeSlot, 5);
			break;

			
		case 5064:
			if (Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				Server.getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot do this whilst trading.");
				return;
			}
			DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERACTION) {
				c.sendMessage("You have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			//Price checker
			if (c.isChecking) {
				PriceChecker.depositItem(c, removeId, 10);
			}
			if (c.isBanking) {
				c.getItems().addToBank(removeId, 10, true);
			}
			break;
			
		case 5382:
			if (Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				Server.getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot do this whilst trading.");
				return;
			}
        	if(c.getBank().getBankSearch().isSearching()) {
        		c.getBank().getBankSearch().removeItem(removeId, 10);
        		return;
        	}
			c.getItems().removeFromBank(removeId, 10, true);
			break;

		case 3322:
			MultiplayerSession session = Server.getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession || session instanceof DuelSession) {
				session.addItem(c, new GameItem(removeId, 10));
			}
			break;

		case 3415:
			session = Server.getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession) {
				session.removeItem(c, removeSlot, new GameItem(removeId, 10));
			}
			break;

		case 6669:
			session = Server.getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof DuelSession) {
				session.removeItem(c, removeSlot, new GameItem(removeId, 10));
			}
			break;

		}
	}

}

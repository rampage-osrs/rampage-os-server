package org.brutality.model.players.packets;

import java.util.Objects;

import org.brutality.Server;
import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.content.DiceNpc;
import org.brutality.model.items.GameItem;
import org.brutality.model.items.ItemDefinition;
import org.brutality.model.items.UseItem;
import org.brutality.model.multiplayer_session.MultiplayerSessionFinalizeType;
import org.brutality.model.multiplayer_session.MultiplayerSessionStage;
import org.brutality.model.multiplayer_session.MultiplayerSessionType;
import org.brutality.model.multiplayer_session.duel.DuelSession;
import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;

public class ItemOnNpc implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		int itemId = packet.getShortA();
		int i = packet.getShortA();
		int slot = packet.getLEShort();
		if(i >= NPCHandler.npcs.length || i < 0 || NPCHandler.npcs[i] == null) {
			return;
		}
		c.npcClickIndex = i;
		int npcId = NPCHandler.npcs[i].npcType;
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		if (!c.canUsePackets) {
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERACTION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}
		
		if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(), c.getX(), c.getY(),
				NPCHandler.npcs[c.npcClickIndex].getSize())) {
			UseItem.ItemonNpc(c, itemId, npcId, slot);
		} else {
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (NPCHandler.npcs[c.npcClickIndex] != null) {
						if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(),
								NPCHandler.npcs[c.npcClickIndex].getSize())) {
							c.face(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
							NPCHandler.npcs[c.npcClickIndex].face(c);
							UseItem.ItemonNpc(c, itemId, npcId, slot);
							container.stop();
						}
					}
					if (c.clickNpcType == 0 || c.clickNpcType > 1)
						container.stop();
				}
			

				@Override
				public void stop() {
					c.clickNpcType = 0;
				}
			}, 1);
		}
	}
}

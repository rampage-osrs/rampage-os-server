package org.brutality.model.players.packets;

import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;

/**
 * Bank X Items
 **/
public class BankX1 implements PacketType {

	public static final int PART1 = 135;
	public static final int PART2 = 208;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

	@Override
	public void processPacket(Player c, Packet packet) {
		if (packet.getOpcode() == 135) {
			c.xRemoveSlot = packet.getLEShort();
			c.xInterfaceId = packet.getUnsignedShortA();
			c.xRemoveId = packet.getLEShort();
		}
		if (c.xInterfaceId == 3900) {
			c.getShops().buyItem(c.xRemoveId, c.xRemoveSlot, 100);// buy 100
			c.xRemoveSlot = 0;
			c.xInterfaceId = 0;
			c.xRemoveId = 0;
			return;
		}

		if (packet.getOpcode() == PART1) {
			//synchronized (c) {
				c.getOutStream().createFrame(27);
			//}
		}

	}
}

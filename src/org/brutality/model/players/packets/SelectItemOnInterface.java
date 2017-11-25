package org.brutality.model.players.packets;

import org.brutality.model.content.presets.Preset;
import org.brutality.model.content.presets.PresetSlotAction;
import org.brutality.model.content.presets.PresetType;
import org.brutality.model.items.GameItem;
import org.brutality.model.items.ItemDefinition;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;

/**
 * @author Jason MacKeigan
 * @date Dec 29, 2014, 1:12:35 PM
 */
public class SelectItemOnInterface implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		int interfaceId = packet.getInt();
		int slot = packet.getInt();
		int itemId = packet.getInt();
		int itemAmount = packet.getInt();
		if (!player.canUsePackets) {
			return;
		}
		switch (interfaceId) {
			case 32011:
				PresetType type = player.getPresets().getCurrent().getEditingType();
				Preset preset = player.getPresets().getCurrent();
				GameItem item = new GameItem(itemId, itemAmount);
				if (type.isEquipment()) {
					ItemDefinition itemDefinition = ItemDefinition.forId(itemId);
					if (itemDefinition != null) {
						int equipmentSlot = PresetSlotAction.getEquipmentSlot(type, preset.getSelectedSlot());
						if (!itemDefinition.isWearable()) {
							player.sendMessage("This item cannot be worn.");
							return;
						}
						if (itemDefinition.getEquipmentSlot() != equipmentSlot) {
							player.sendMessage("This item cannot be inserted into this equipment slot.");
							return;
						}
					} else {
						player.sendMessage("This item is currently unavailable.");
						return;
					}
					preset.getEquipment().add(player, preset.getSelectedSlot(), item);
				} else if (type.isInventory()) {
					preset.getInventory().add(player, preset.getSelectedSlot(), item);
				}
				player.getPresets().hideSearch();
				break;
		}
	}

}

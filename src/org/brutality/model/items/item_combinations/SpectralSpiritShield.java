package org.brutality.model.items.item_combinations;

import java.util.List;
import java.util.Optional;

import org.brutality.model.items.GameItem;
import org.brutality.model.items.ItemCombination;
import org.brutality.model.players.Player;

public class SpectralSpiritShield extends ItemCombination {

	public SpectralSpiritShield(Optional<int[]> skillRequirements, GameItem outcome, Optional<List<GameItem>> revertedItems, GameItem[] items) {
		super(skillRequirements, outcome, revertedItems, items);
	}

	@Override
	public void combine(Player player) {
		if (player.playerLevel[13] < 85) {
			player.getDH().sendStatement("You must have a Smithing level of 85 to craft this.");
			return;
		}
		super.items.forEach(item -> player.getItems().deleteItem2(item.getId(), item.getAmount()));
		player.getItems().addItem(super.outcome.getId(), super.outcome.getAmount());
		player.getDH().sendStatement("You combined the items and created the Spectral Spirit Shield.");
		player.setCurrentCombination(Optional.empty());
		player.nextChat = -1;
	}
	
	@Override
	public void showDialogue(Player player) {
		player.getDH().sendStatement("Once the sigil is combined with the blessed spirit shield",
				"there is no going back. The items cannot be reverted.");
	}

}

package org.brutality.model.items.item_combinations;

import java.util.List;
import java.util.Optional;

import org.brutality.model.items.GameItem;
import org.brutality.model.items.ItemCombination;
import org.brutality.model.players.Player;

public class OdiumWard extends ItemCombination {

	public OdiumWard(Optional<int[]> skillRequirements, GameItem outcome, Optional<List<GameItem>> revertedItems, GameItem[] items) {
		super(skillRequirements, outcome, revertedItems, items);
	}
	
	@Override
	public void combine(Player player) {
		super.items.forEach(item -> player.getItems().deleteItem2(item.getId(), item.getAmount()));
		player.getItems().addItem(super.outcome.getId(), super.outcome.getAmount());
		player.getDH().sendStatement("You combined the items and created the Odium ward (or).");
		player.setCurrentCombination(Optional.empty());
		player.nextChat = -1;
	}
	
	@Override
	public void showDialogue(Player player) {
		player.getDH().sendStatement("A Odium ward (or) is untradeable.",
				"You can revert this but you will lose the upgrade kit.");
	}

}

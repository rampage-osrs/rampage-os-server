package org.brutality.model.items.item_combinations;

import java.util.List;
import java.util.Optional;

import org.brutality.model.items.GameItem;
import org.brutality.model.items.ItemCombination;
import org.brutality.model.players.Player;

public class BlueDarkBow extends ItemCombination {

	public BlueDarkBow(Optional<int[]> skillRequirements, GameItem outcome, Optional<List<GameItem>> revertedItems, GameItem[] items) {
		super(skillRequirements, outcome, revertedItems, items);
	}
	
	@Override
	public void combine(Player player) {
		super.items.forEach(item -> player.getItems().deleteItem2(item.getId(), item.getAmount()));
		player.getItems().addItem(super.outcome.getId(), super.outcome.getAmount());
		player.getDH().sendStatement("You combined the items and created the Blue dark bow.");
		player.setCurrentCombination(Optional.empty());
		player.nextChat = -1;
	}
	
	@Override
	public void showDialogue(Player player) {
		player.getDH().sendStatement("The Blue dark bow is untradeable.", 
				"You cannot revert this item either.");
	}

}

package org.brutality.model.minigames.bounty_hunter.events;

import org.brutality.event.CycleEventContainer;
import org.brutality.model.minigames.bounty_hunter.BountyHunter;
import org.brutality.model.minigames.bounty_hunter.TargetEvent;
import org.brutality.model.minigames.bounty_hunter.TargetState;
import org.brutality.model.players.Player;

public class TargetDelay extends TargetEvent {

	public TargetDelay(BountyHunter bountyHunter) {
		super(bountyHunter);
	}

	@Override
	public void execute(CycleEventContainer container) {
		BountyHunter bh = super.bountyHunter;
		Player player = bh.getPlayer();
		if (bh.getTargetState().hasKilledRecently()) {
			bh.setDelayedTargetTicks(bh.getDelayedTargetTicks() - 1);
			if (bh.getDelayedTargetTicks() <= 0) {
				bh.setTargetState(TargetState.NONE);
				bh.setDelayedTargetTicks(BountyHunter.MAXIMUM_WARNING_TICKS);
				bh.updateTargetUI();
				container.stop();
			}
		} else if (bh.getTargetState().isPenalized()) {
			if (!player.inWild()) {
				return;
			}
			bh.setDelayedTargetTicks(bh.getDelayedTargetTicks() - 1);
			if (bh.getDelayedTargetTicks() <= 0) {
				bh.setWarnings(0);
				bh.setDelayedTargetTicks(BountyHunter.MAXIMUM_WARNING_TICKS);
				bh.setTargetState(TargetState.NONE);
				bh.updateTargetUI();
				player.sendMessage("You are no longer being penalized. Please avoid logging out and leaving");
				player.sendMessage("the wilderness for longer than two minutes whilst having a target.");
				container.stop();
			}
		} else {
			container.stop();
		}
	}
	
}

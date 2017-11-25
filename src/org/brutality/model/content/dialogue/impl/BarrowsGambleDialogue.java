package org.brutality.model.content.dialogue.impl;

import org.brutality.model.content.BarrowsGamble;
import org.brutality.model.content.dialogue.Dialogue;
import org.brutality.model.content.dialogue.DialogueManager;
import org.brutality.model.content.dialogue.Emotion;
import org.brutality.model.content.dialogue.OptionDialogue;

public class BarrowsGambleDialogue extends Dialogue {

	@Override
	public void execute() {
		switch (getNext()) {
		case 0:
			DialogueManager.sendNpcChat(getPlayer(), NPC_ID, Emotion.LAUGHING, "Hello traveller!");
			setNext(1);
			break;
		case 1:
			DialogueManager.sendNpcChat(getPlayer(), NPC_ID, Emotion.LAUGHING, "Would you like to gamble two barrows pieces", "for a single random piece?");
			setNext(2);
			break;
		case 2:
			getPlayer().start(new OptionDialogue("Gamble two barrows pieces.", p -> {
				BarrowsGamble.gamble(getPlayer());
				getPlayer().getPA().removeAllWindows();
			}, "Don't gamble.", p -> {
				getPlayer().getPA().removeAllWindows();
			}));
			end();
		}
	}

	public static final int NPC_ID = 3562;

}
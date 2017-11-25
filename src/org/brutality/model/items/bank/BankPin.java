package org.brutality.model.items.bank;

import org.brutality.model.players.Player;

/**
 * 
 * @author Jason MacKeigan
 * @date July 10th, 2014, 2:41:21 PM
 */
public class BankPin {

	private Player player;
	private String pin = "";
	private boolean locked = true;
	private long cancellationDelay = -1;
	private boolean appendingCancellation;
	private int attempts;
	private PinState pinState;

	public BankPin(Player player) {
		this.player = player;
	}
	
	public enum PinState {
		CREATE_NEW, UNLOCK, CANCEL_PIN, CANCEL_REQUEST
	}

	public void open(int state) {
		player.getPA().sendFrame126("", 59507);
		switch(state) {
			case 1:
				pinState = PinState.CREATE_NEW;
				player.getPA().sendFrame126("You do not have a pin set.", 59503);
				player.getPA().sendFrame126("Choose any 4-8 character combination.", 59504);
				player.getPA().sendFrame126("Make sure caps lock isn't enabled.", 59505);
				player.getPA().sendFrame126("Press enter to continue", 59506);
				//player.getPA().sendFrame171(1, 59511);
				break;
			case 2:
				pinState = PinState.UNLOCK;
				player.getPA().sendFrame126("You currently have a pin set.", 59503);
				player.getPA().sendFrame126("Type in your 4-8 character combination.", 59504);
				player.getPA().sendFrame126("Hit enter after you've typed your pin.", 59505);
				player.getPA().sendFrame126("Press the button to continue", 59506);
				//player.getPA().sendFrame171(1, 59511);
				break;
			case 3:
				pinState = PinState.CANCEL_PIN;
				player.getPA().sendFrame126("If you wish to cancel your pin, ", 59503);
				player.getPA().sendFrame126("click the button below. If not", 59504);
				player.getPA().sendFrame126("click the x button in the corner.", 59505);
				player.getPA().sendFrame126("Press the button to continue", 59506);
				//player.getPA().sendFrame171(0, 59511);
				break;
			case 4:
				pinState = PinState.CANCEL_REQUEST;
				player.getPA().sendFrame126("Your current pin cancellation is", 59503);
				player.getPA().sendFrame126("pending. Press continue to cancel", 59504);
				player.getPA().sendFrame126("this and keep your bank pin.", 59505);
				player.getPA().sendFrame126("Press the button to continue", 59506);
				//player.getPA().sendFrame171(1, 59511);
				break;
		}
		player.getPA().showInterface(59500);
	}
	
	public void create(String pin) {
		if(this.pin.length() > 0) {
			player.sendMessage("You already have a pin, you cannot create another one.");
			return;
		}
		if(pin.length() < 4) {
			player.sendMessage("Your pin must be atleast 4 characters in length.");
			return;
		}
		if(pin.length() > 8) {
			player.sendMessage("Your pin cannot be longer than 8 characters in length.");
			return;
		}
		if(!pin.matches("[A-Za-z0-9]+")) {
			player.sendMessage("Your bank pin contains illegal characters. Pins can only contain numbers,");
			player.sendMessage("and uppercase, and lowercase case letters.");
			return;
		}
		if(pin.contains(" ")) {
			player.sendMessage("Your bank pin contains 1 or more spaces, bank pins cannot contain spaces.");
			return;
		}
		if(pin.equalsIgnoreCase(player.playerName)) {
			player.sendMessage("Your bank pin cannot match your username.");
			return;
		}
		player.sendMessage("You have sucessfully created a bank pin. We urge you to keep this combination");
		player.sendMessage("to yourself as sharing it may jepordize the items you have in your bank.");
		this.pin = pin;
		this.locked = true;
		this.attempts = 0;
		player.getPA().openUpBank();
	}
	
	public void unlock(String pin) {
		if(!this.locked) {
			return;
		}
		if(!this.pin.equals(pin)) {
			player.sendMessage("The pin you entered does not match your current bank pin, please try again.");
			this.attempts++;
			return;
		}
		this.player.getPA().closeAllWindows();
		this.attempts = 0;
		this.locked = false;
		this.player.playerStun = false;
		player.sendMessage("You have successfully entered your "+this.pin.length()+" character pin");
		this.update();
		player.getPA().openUpBank();
	}
	
	public void cancel(String pin) {
		if(!this.pin.equals(pin)) {
			player.sendMessage("The pin you entered does not match your current bank pin, please try again.");
			this.attempts++;
			return;
		}
		if(this.pinState == PinState.CANCEL_PIN) {
			this.setAppendingCancellation(true);
			this.cancellationDelay = System.currentTimeMillis();
			this.player.sendMessage("Your pin is currently pending cancellation and will expire in 3 days.");
			this.player.sendMessage("If you wish to cancel this, simply open up this interface again.");
		} else if(this.pinState == PinState.CANCEL_REQUEST) {
			this.setAppendingCancellation(false);
			this.cancellationDelay = -1;
			this.player.sendMessage("Your pin is no longer scheduled for cancellation.");
		}
	}
	
	public void update() {
		if(this.appendingCancellation) {
			if(System.currentTimeMillis() - this.cancellationDelay > (1000 * 60 * 60 * 24 * 3)) {
				this.pin = "";
				this.cancellationDelay = -1;
				this.attempts = 0;
				this.locked = false;
				this.appendingCancellation = false;
				player.sendMessage("Your pin has successfully been reset. If you wish to set another pin, you may do so.");
			} else {
				final long daysRemaining = (((1000 * 60 * 60 * 24 * 3) - (System.currentTimeMillis() - this.cancellationDelay)) / 1000 / 60 / 60 / 24);
				player.sendMessage("Your pin is pending cancellation and will be reset in " + 
						(daysRemaining < 1 ? daysRemaining * 24 + " hours" : daysRemaining + " days"));
			}
		}
	}
	
	public boolean requiresUnlock() {
		return locked && pin.length() > 0;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public long getCancellationDelay() {
		return cancellationDelay;
	}

	public void setCancellationDelay(long cancellationDelay) {
		this.cancellationDelay = cancellationDelay;
	}

	public boolean isAppendingCancellation() {
		return appendingCancellation;
	}

	public void setAppendingCancellation(boolean appendingCancellation) {
		this.appendingCancellation = appendingCancellation;
	}

	public PinState getPinState() {
		return pinState;
	}

	public void setPinState(PinState pinState) {
		this.pinState = pinState;
	}
	
	
}

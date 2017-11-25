package org.brutality.model.items.bank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.brutality.Config;

/**
 * 
 * @author Jason http://www.rune-server.org/members/jason
 * @date Apr 11, 2014
 * Revised By: 01053.
 */
public class BankTab {

	final List<BankItem> bankItems = new ArrayList<>();
	private int tabId;

	public BankTab() {

	}

	/**
	 * 
	 * @param tabId
	 *            The bank tab id
	 */
	public BankTab(int tabId) {
		this.setTabId(tabId);
	}

	/**
	 *
	 * @param bankItem
	 *            The object that contains the item id and amount
	 */
	public void add(BankItem bankItem) {
		if (bankItem.getAmount() <= 0)
			return;
		for (BankItem item : bankItems) {
			
			if (item.getId() == bankItem.getId()) {
				item.setAmount(bankItem.getAmount() + item.getAmount());
				if (item.getAmount() > Integer.MAX_VALUE)
					item.setAmount(Integer.MAX_VALUE);
				return;
			}
		}
		bankItems.add(bankItem);
	}

	/**
	 * 
	 * @param bankItem
	 *            Removes the BankItem object from the ArrayList
	 */
	public void remove(BankItem bankItem) {

		Iterator<BankItem> $it = bankItems.iterator();
		while ($it.hasNext()) {
			BankItem item = $it.next();
			if (item != null && item.getId() == bankItem.getId()) {
				if (item.getAmount() - bankItem.getAmount() <= 0)
					$it.remove();
				else
					item.setAmount(item.getAmount() - bankItem.getAmount());
				break;
			}
		}
	}

	/**
	 * 
	 * @return The current amount of items in the bank tab
	 */
	public int size() {
		return bankItems.size();
	}

	/**
	 * 
	 * @return The amount of free slots remaining in this tab
	 */
	public int freeSlots() {
		return Config.BANK_SIZE - bankItems.size();
	}

	public boolean contains(BankItem bankItem) {
		for (int i = 0; i < bankItems.size(); i++)
			if (bankItems.get(i) != null)
				if (bankItems.get(i).getId() == bankItem.getId())
					return true;
		return false;
	}

	public boolean containsAmount(BankItem bankItem) {
		for (int i = 0; i < bankItems.size(); i++)
			if (bankItems.get(i) != null)
				if (bankItems.get(i).getId() == bankItem.getId())
					return bankItems.get(i).getAmount() >= bankItem.getAmount();
		return false;
	}

	public boolean spaceAvailable(BankItem bankItem, int offset) {
		for (int i = 0; i < bankItems.size(); i++)
			if (bankItems.get(i) != null)
				if (bankItems.get(i).getId() == bankItem.getId())
					return bankItems.get(i).getAmount() + offset < Integer.MAX_VALUE;
		return false;
	}

	public boolean spaceAvailable(BankItem bankItem) {
		for (int i = 0; i < bankItems.size(); i++)
			if (bankItems.get(i) != null)
				if (bankItems.get(i).getId() == bankItem.getId())
					return bankItems.get(i).getAmount() + bankItem.getAmount() < Integer.MAX_VALUE;
		return true;
	}

	public int getItemAmount(BankItem bankItem) {
		for (BankItem item : bankItems)
			if (item.getId() == bankItem.getId())
				return item.getAmount();
		return 0;
	}

	public BankItem getItem(int slot) {
		if (slot < 0 || slot > bankItems.size()) {
			return null;
		}
		return bankItems.get(slot);
	}

	public BankItem getItem(BankItem item) {
		for (BankItem items : bankItems)
			if (items.getId() == item.getId())
				return items;
		return null;
	}

	public void setItem(int slot, BankItem item) {
		bankItems.set(slot, item);
	}

	public List<BankItem> getItems() {
		return bankItems;
	}

	public int getTabId() {
		return tabId;
	}

	public void setTabId(int tabId) {
		this.tabId = tabId;
	}

}

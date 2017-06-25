package the.professorzoom.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;
import the.professorzoom.Withdraw;
import the.professorzoom.utils.ChatApplicable;
import the.professorzoom.utils.ItemBuilder;
import the.professorzoom.utils.ChatApplicable.MESSAGE;

public class GraphicalUserInterfaceListener implements Listener {

	@Getter @Setter
	private List<UUID> viewing;
	@Getter @Setter
	private Inventory inventory;

	public GraphicalUserInterfaceListener() {
		Bukkit.getPluginManager().registerEvents(this, Withdraw.getInstance());
		setViewing(new ArrayList<UUID>());
		createInventory();
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent ev) {
		Player player = (Player) ev.getWhoClicked();
		
		if (getViewing().contains(player.getUniqueId())) {
			
			ev.setCancelled(true);
			
			if (ev.getCurrentItem() == null || ev.getCurrentItem().getType() == Material.STAINED_GLASS_PANE)
				return;
			
			handleItem(player, ev.getSlot());
			return;
			
		}
		
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent ev) {
		Player player = (Player) ev.getPlayer();

		if (getViewing().contains(player.getUniqueId()))
			getViewing().remove(player.getUniqueId());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent ev) {
		Player player = ev.getPlayer();

		if (getViewing().contains(player.getUniqueId()))
			getViewing().remove(player.getUniqueId());
	}

	public void createInventory() {
		setInventory(Bukkit.createInventory(null, 27, ChatApplicable.format(MESSAGE.INVENTORY_NAME.getMessage())));
		Inventory inv = getInventory();

		inv.setItem(Integer.parseInt(MESSAGE.ITEM_ONE_SLOT.getMessage()), getItem(MESSAGE.ITEM_ONE_MATE.getMessage(),
				MESSAGE.ITEM_ONE_NAME.getMessage(), MESSAGE.ITEM_ONE_LORE.getMessage()));
		inv.setItem(Integer.parseInt(MESSAGE.ITEM_TWO_SLOT.getMessage()), getItem(MESSAGE.ITEM_TWO_MATE.getMessage(),
				MESSAGE.ITEM_TWO_NAME.getMessage(), MESSAGE.ITEM_TWO_LORE.getMessage()));
		inv.setItem(Integer.parseInt(MESSAGE.ITEM_THREE_SLOT.getMessage()), getItem(MESSAGE.ITEM_THREE_MATE.getMessage(),
				MESSAGE.ITEM_THREE_NAME.getMessage(), MESSAGE.ITEM_THREE_LORE.getMessage()));
		inv.setItem(Integer.parseInt(MESSAGE.ITEM_FOUR_SLOT.getMessage()), getItem(MESSAGE.ITEM_FOUR_MATE.getMessage(),
				MESSAGE.ITEM_FOUR_NAME.getMessage(), MESSAGE.ITEM_FOUR_LORE.getMessage()));
		inv.setItem(Integer.parseInt(MESSAGE.ITEM_FIVE_SLOT.getMessage()), getItem(MESSAGE.ITEM_FIVE_MATE.getMessage(),
				MESSAGE.ITEM_FIVE_NAME.getMessage(), MESSAGE.ITEM_FIVE_LORE.getMessage()));	
		inv.setItem(Integer.parseInt(MESSAGE.ITEM_SIX_SLOT.getMessage()), getItem(MESSAGE.ITEM_SIX_MATE.getMessage(),
				MESSAGE.ITEM_SIX_NAME.getMessage(), MESSAGE.ITEM_SIX_LORE.getMessage()));
		inv.setItem(Integer.parseInt(MESSAGE.ITEM_SEVEN_SLOT.getMessage()), getItem(MESSAGE.ITEM_SEVEN_MATE.getMessage(),
				MESSAGE.ITEM_SEVEN_NAME.getMessage(), MESSAGE.ITEM_SEVEN_LORE.getMessage()));
	
		ItemBuilder.fillEmpty(inv);
	}

	public ItemStack getItem(String mat, String name, String lores) {
		List<String> lore = new ArrayList<String>();
		String[] args = lores.split("!");
		for (int i = 0; i < args.length; i++)
			lore.add(ChatApplicable.format(args[i]));
		return ItemBuilder.build(Material.valueOf(mat), name, lore, 1);
	}
	
	public void handleItem(Player player, Integer slot) {
		int SLOT_ONE = Integer.parseInt(MESSAGE.ITEM_ONE_SLOT.getMessage());
		int SLOT_TWO = Integer.parseInt(MESSAGE.ITEM_TWO_SLOT.getMessage());
		int SLOT_THREE = Integer.parseInt(MESSAGE.ITEM_THREE_SLOT.getMessage());
		int SLOT_FOUR = Integer.parseInt(MESSAGE.ITEM_FOUR_SLOT.getMessage());
		int SLOT_FIVE = Integer.parseInt(MESSAGE.ITEM_FIVE_SLOT.getMessage());
		int SLOT_SIX = Integer.parseInt(MESSAGE.ITEM_SIX_SLOT.getMessage());
		int SLOT_SEVEN = Integer.parseInt(MESSAGE.ITEM_SEVEN_SLOT.getMessage());
		
		if (SLOT_ONE == slot)   withdraw(player, 1);		
		if (SLOT_TWO == slot)   withdraw(player, 10);		
		if (SLOT_THREE == slot) withdraw(player, 100);		
		if (SLOT_FOUR == slot)  withdraw(player, 1000);		
		if (SLOT_FIVE == slot)  withdraw(player, 10000);		
		if (SLOT_SIX == slot)   withdraw(player, 100000);
		if (SLOT_SEVEN == slot) withdraw(player, 100000);
		player.closeInventory();
	}
	
	public void withdraw(Player player, int amount) {
		double balance = Withdraw.getInstance().getEconomy().getBalance(player);
		if (balance >= amount) {
			Withdraw.getInstance().getEconomy().withdrawPlayer(player, amount);
			player.getInventory().addItem(getItem(MESSAGE.BANKNOTE_MATERIAL.getMessage(), (MESSAGE.BANKNOTE_NAME.getMessage().replace("!amount", ""+amount)), MESSAGE.BANKENOTE_LORE.getMessage()));
			player.sendMessage(ChatApplicable.format(MESSAGE.SUCCESS.getMessage()));
			return;
		}
		player.sendMessage(ChatApplicable.format(MESSAGE.NOT_ENOUGH_MONEY.getMessage()));
		return;
	}

}

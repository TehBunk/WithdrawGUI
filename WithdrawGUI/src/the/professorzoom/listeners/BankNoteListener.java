package the.professorzoom.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import the.professorzoom.Withdraw;
import the.professorzoom.utils.ChatApplicable;
import the.professorzoom.utils.ChatApplicable.MESSAGE;

public class BankNoteListener implements Listener {

	public BankNoteListener() {
		Bukkit.getPluginManager().registerEvents(this, Withdraw.getInstance());
	}

	@EventHandler
	public void onPlayerUse(PlayerInteractEvent ev) {
		Player player = ev.getPlayer();

		if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR
				|| ev.getAction() != Action.RIGHT_CLICK_BLOCK && ev.getAction() != Action.RIGHT_CLICK_AIR)
			return;

		if (isItem(player.getItemInHand())) {

			Withdraw.getInstance().getEconomy().depositPlayer(player, getAmount(player.getItemInHand()));
			player.sendMessage(ChatApplicable.format(MESSAGE.COLLECTED.getMessage()));
			int amount = (player.getItemInHand().getAmount() - 1);
			player.setItemInHand(amount==0?null:prev(player.getItemInHand(), amount));
			player.updateInventory();
			return;
		}

	}

	public boolean isItem(ItemStack item) {
		if (item.getType() != Material.valueOf(MESSAGE.BANKNOTE_MATERIAL.getMessage()) || !item.hasItemMeta()
				|| !item.getItemMeta().hasLore() || !item.getItemMeta().hasDisplayName())
			return false;

		List<String> lore = new ArrayList<String>();
		String[] args = MESSAGE.BANKENOTE_LORE.getMessage().split("!");
		for (int i = 0; i < args.length; i++)
			lore.add(ChatApplicable.format(args[i]));

		if (!lore.isEmpty() && !ChatApplicable.format(item.getItemMeta().getLore().get(0)).equals(lore.get(0)))
			return false;

		if (!ChatApplicable.format(item.getItemMeta().getDisplayName()).contains(ChatApplicable.format(MESSAGE.BANKNOTE_NAME.getMessage().replace("!amount", ""))))
			return false;
		
		return true;
	}
	
	public Integer getAmount(ItemStack item) {
		String dn = ChatColor.stripColor(item.getItemMeta().getDisplayName()).replace(":", "").replace(" ", "").replace("$", "");
		String str = dn.replaceAll("[a-zA-Z]", "");
		return Integer.parseInt(str);
	}

	public ItemStack prev(ItemStack item, int amount) {
		ItemStack itemz = new ItemStack(item);
		itemz.setAmount(amount);
		return itemz;
	}
}

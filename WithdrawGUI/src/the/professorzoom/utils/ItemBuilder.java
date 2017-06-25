package the.professorzoom.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	public static ItemStack build(Material material, String name, List<String> lore, int amount) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta im = item.getItemMeta();
		if (name != null) im.setDisplayName(ChatApplicable.format(name));
		if (lore != null) im.setLore(ChatApplicable.format(lore));
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack buildFiller() {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatApplicable.format("&f&r"));
		item.setItemMeta(im);
		return item;
	}
	
	public static void fillEmpty(Inventory inv) {
		int a = 0;
		for (ItemStack item : inv.getContents()) {
			if (item == null || item.getType() == Material.AIR)
				inv.setItem(a, buildFiller());
			a++;
		}
	}
	
}

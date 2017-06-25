package the.professorzoom.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import lombok.Setter;
import the.professorzoom.Withdraw;

public class ChatApplicable {

	public static String format(String str) {
		return (ChatColor.translateAlternateColorCodes('&', str));
	}

	public static List<String> format(List<String> strList) {
		List<String> formatted = new ArrayList<String>();
		for (String str : strList)
			formatted.add(format(str));
		return formatted;
	}
	
	public ChatApplicable() {

		File dir = Withdraw.getInstance().getDataFolder(), config = null;

		if (!dir.exists())
			dir.mkdir();

		config = new File(dir, "config.yml");

		boolean created = false;

		if (!config.exists()) {
			try {

				config.createNewFile();

				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);

				for (MESSAGE selection : MESSAGE.values())
					cfg.set(selection.toString(), selection.getMessage());

				cfg.save(config);
				created = true;

			} catch (IOException e) {
				Bukkit.getConsoleSender()
						.sendMessage("&a----------------------[IOException Error]----------------------");
				e.printStackTrace();
				Bukkit.getConsoleSender()
						.sendMessage("&a----------------------[IOException Error]----------------------");
			}

		}

		if (!created) {

			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);

			for (MESSAGE selection : MESSAGE.values()) {
				if (!cfg.contains(selection.toString())) {
					cfg.set(selection.toString(), selection.getMessage());
					continue;
				}
				if (cfg.getString(selection.toString()) != selection.getMessage())
					selection.setMessage(cfg.getString(selection.toString()));
			}
			
			try {
				cfg.save(config);
			} catch (IOException e) {
				Bukkit.getConsoleSender()
						.sendMessage("&a----------------------[IOException Error]----------------------");
				e.printStackTrace();
				Bukkit.getConsoleSender()
						.sendMessage("&a----------------------[IOException Error]----------------------");
			}

		}

	}

	public enum MESSAGE {
		CONSOLE_SENDER("&cThat feature is for players only!"),
		NO_PERMISSION("&cYou do not have permission to do that!"),
		NOT_ENOUGH_MONEY("&cYou do not have enough money to do that!"),
		SUCCESS("&aYou have been given a BankNote!"),
		COLLECTED("&aYou have collected a BankNote!"),
		
		INVENTORY_NAME("&6&lWithdraw..."),
		
		ITEM_ONE_NAME("&6&lWithdraw&7: &e$1"),
		ITEM_ONE_LORE("&7Apon clicking this you!&7will recive a 1$ Bank note."),
		ITEM_ONE_MATE("PAPER"),
		ITEM_ONE_SLOT("10"),
		
		ITEM_TWO_NAME("&6&lWithdraw&7: &e$10"),
		ITEM_TWO_LORE("&7Apon clicking this you!&7will recive a 10$ Bank note."),
		ITEM_TWO_MATE("PAPER"),
		ITEM_TWO_SLOT("11"),
		
		ITEM_THREE_NAME("&6&lWithdraw&7: &e$100"),
		ITEM_THREE_LORE("&7Apon clicking this you!&7will recive a 100$ Bank note."),
		ITEM_THREE_MATE("PAPER"),
		ITEM_THREE_SLOT("12"),
		
		ITEM_FOUR_NAME("&6&lWithdraw&7: &e$1000"),
		ITEM_FOUR_LORE("&7Apon clicking this you!&7will recive a 1000$ Bank note."),
		ITEM_FOUR_MATE("PAPER"),
		ITEM_FOUR_SLOT("13"),
		
		ITEM_FIVE_NAME("&6&lWithdraw&7: &e$10000"),
		ITEM_FIVE_LORE("&7Apon clicking this you!&7will recive a 10000$ Bank note."),
		ITEM_FIVE_MATE("PAPER"),
		ITEM_FIVE_SLOT("14"),
		
		ITEM_SIX_NAME("&6&lWithdraw&7: &e$100000"),
		ITEM_SIX_LORE("&7Apon clicking this you!&7will recive a 100000$ Bank note."),
		ITEM_SIX_MATE("PAPER"),
		ITEM_SIX_SLOT("15"),
		
		ITEM_SEVEN_NAME("&6&lWithdraw&7: &e$1000000"),
		ITEM_SEVEN_LORE("&7Apon clicking this you!&7will recive a 1000000$ Bank note."),
		ITEM_SEVEN_MATE("PAPER"),
		ITEM_SEVEN_SLOT("16"),
		
		BANKNOTE_NAME("&b&lBanknote:&7$!amount"),
		BANKENOTE_LORE("&7Click me to collect your money!"),
		BANKNOTE_MATERIAL("GOLD_INGOT")
		
		
		
		
		;
		@Getter @Setter
		private String message;

		MESSAGE(String str) {
			setMessage(str);
		}
	}
}

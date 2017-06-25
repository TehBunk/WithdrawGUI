package the.professorzoom;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import the.professorzoom.commands.WithdrawCommand;
import the.professorzoom.listeners.BankNoteListener;
import the.professorzoom.listeners.GraphicalUserInterfaceListener;
import the.professorzoom.utils.ChatApplicable;

public class Withdraw extends JavaPlugin {

	@Getter @Setter // Instance of this Main class
	private static Withdraw instance;
	@Getter @Setter
	private GraphicalUserInterfaceListener GUIListner;
	@Getter @Setter
	private Economy economy;

	@Override // Method called when plugin is reloaded / started
	public void onEnable() {
		if (getVault() == null || !loadEco()) {
			Bukkit.getConsoleSender().sendMessage(ChatApplicable.format("&cPLUGIN DISABLING, ECONOMY & VAULT ARE REQUIRED"));
			setInstance(null);
			onDisable();
			return;
		}
		
		setInstance(this);
		new ChatApplicable();
		setGUIListner(new GraphicalUserInterfaceListener());
		new BankNoteListener();
		new WithdrawCommand();
		
	}

	public Vault getVault() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Vault");
		if (plugin == null || !(plugin instanceof Vault))
			return null;
		return (Vault) plugin;	
	}
	
	public boolean loadEco() { 
		try {
			RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
			setEconomy(rsp.getProvider());
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}

}

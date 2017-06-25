package the.professorzoom.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import the.professorzoom.Withdraw;
import the.professorzoom.utils.ChatApplicable;
import the.professorzoom.utils.ChatApplicable.MESSAGE;

public class WithdrawCommand implements CommandExecutor {

	public WithdrawCommand() {
		Withdraw.getInstance().getCommand("Withdraw").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatApplicable.format(MESSAGE.CONSOLE_SENDER.getMessage()));
			return false;
		}
		
		if (!sender.hasPermission("withdraw.use")) {
			sender.sendMessage(ChatApplicable.format(MESSAGE.NO_PERMISSION.getMessage()));
			return false;
		}
		
		Player player = (Player) sender;
		
		if (Withdraw.getInstance().getGUIListner().getViewing().contains(player.getUniqueId()))
				Withdraw.getInstance().getGUIListner().getViewing().remove(player.getUniqueId());
		
		player.openInventory(Withdraw.getInstance().getGUIListner().getInventory());
		Withdraw.getInstance().getGUIListner().getViewing().add(player.getUniqueId());
		return true;
	}
}

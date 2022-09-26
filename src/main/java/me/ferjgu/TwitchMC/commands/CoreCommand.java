package me.ferjgu.TwitchMC.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import me.ferjgu.TwitchMC.Main;
import me.ferjgu.utils.Utils;
import me.ferjgu.utils.command.CommandBuilder;
import me.ferjgu.utils.command.SubCommandBuilder;

public class CoreCommand extends CommandBuilder {

	public CoreCommand(Main instance, String commandName) {
		super(instance, commandName);
		
		this.setupCommand(new SubCommandBuilder("reload","twitchmc.command.core.reload"){
			@Override
			public void onCommand(CommandSender sender, Command command, String[] args) {
				plugin.reloadPlugin();
				sender.sendMessage(Utils.prefixColor("Reloaded plugin."));
			}
		},
				new SubCommandBuilder("config", "twitchmc.command.core.configs") {
			@Override
			public void onCommand(CommandSender sender, Command command, String[] args) {
				String output = "";
				for(String s : plugin.mainConfig.data.getKeys(true)) {
					output += "- " + s + " = " + plugin.mainConfig.data.getString(s) + "\n";
				}
				sender.sendMessage("Config nodes:");
				sender.sendMessage(output);
			}
		});
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if(super.onCommand(sender, command, label, args)) {
			return true;
		}
		return false;
	}
}

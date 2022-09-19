package me.ferjgu.utils.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import me.ferjgu.TwitchMC.Main;
import me.ferjgu.utils.Utils;

public class CommandBuilder implements CommandExecutor {
	
	private Main plugin;
	private HashMap<String, SubCommandBuilder> subcommands;
	private List<HashMap<String, SubCommandBuilder>> commandTree;
	private PluginCommand thisCommand;
	public CommandBuilder(Main instance, String commandName) {
		this.plugin = instance;
		this.thisCommand = this.plugin.getCommand(commandName);
		this.thisCommand.setExecutor(this);
		this.subcommands = null;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, 
			@NotNull String label, @NotNull String[] args) {
		if(this.subcommands != null) {
			if(this.subcommands.keySet().contains(args[0].toLowerCase())) {
				this.subcommands.get(args[0].toLowerCase()).onCommand(sender, command, args);
			} else {
				sender.sendMessage(Utils.color("&cInvalid subcommand."));
				return false;
			}
		}
		return false;
	}
	
	public void setupCommand(SubCommandBuilder ... builders) {
		setSubcommands(builders);
		if(builders != null) {
			setTabCompleter();
		}
	}
	
	private void setSubcommands(SubCommandBuilder ... builders) {
		this.subcommands = new HashMap<String, SubCommandBuilder>();
		for(SubCommandBuilder s : builders) {this.subcommands.put(s.getName(), s);}
	}
	
	private void setTabCompleter() {
		this.thisCommand.setTabCompleter(new TabCompleterBuilder(this));
	}
	
	public HashMap<String, SubCommandBuilder> getSubcommands(){
		return this.subcommands;
	}

}

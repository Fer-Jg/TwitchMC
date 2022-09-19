package me.ferjgu.utils.command;

import java.util.HashMap;

import javax.annotation.Nonnull;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SubCommandBuilder {
	
	private String commandName;
	private String permission;
	private HashMap<String, SubCommandBuilder> subcommands;
	
	public SubCommandBuilder(String name, String permission) {
		setPermission(permission);
		this.commandName = name;
		this.subcommands = null;
	}
	
	public SubCommandBuilder(String name, String permission, @Nonnull SubCommandBuilder... builders) {
		setPermission(permission);
		this.commandName = name;
		setSubcommands(builders);
	}
	
	public void onCommand(CommandSender sender, Command command, String args[]) {}
	
	public SubCommandBuilder build() {
		return this;
	}
	
	private void setSubcommands(SubCommandBuilder ... builders) {
		if(builders.length < 1) return;
		this.subcommands = new HashMap<String, SubCommandBuilder>();
		for(SubCommandBuilder s : builders) {this.subcommands.put(s.getName(), s);}
	}
	
	public HashMap<String, SubCommandBuilder> getSubcommands(){
		return this.subcommands;
	}
	
	public boolean hasSubcommands() {
		return this.subcommands != null;
	}

	public String getName() { return commandName;}
	
	public String getPermission() {return permission;}
	public void setPermission(String permission) {this.permission = permission;}
}

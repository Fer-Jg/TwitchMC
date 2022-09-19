package me.ferjgu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompleterBuilder implements TabCompleter {
	
	private List<Set<String>> positionArgs;
	@SafeVarargs
	public TabCompleterBuilder(@Nonnull Set<String>... options) {
		this.positionArgs = new ArrayList<Set<String>>();
		for(Set<String> position : options)	this.positionArgs.add(position);
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> result = new ArrayList<String>();
		if(positionArgs.size() < args.length) return null;
		
		for(String arg : positionArgs.get(args.length-1)) {
			if(arg.toLowerCase().startsWith(args[args.length-1].toLowerCase()))
				result.add(arg);
		}
		return result;
	}

}

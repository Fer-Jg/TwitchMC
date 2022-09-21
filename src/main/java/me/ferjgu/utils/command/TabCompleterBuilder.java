package me.ferjgu.utils.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompleterBuilder implements TabCompleter {
	
	private List<Set<String>> positionArgs;
	
	public TabCompleterBuilder(@Nonnull CommandBuilder builder) {
		int index = 0;
		this.positionArgs = new ArrayList<Set<String>>();
		this.positionArgs.add(index, builder.getSubcommands().keySet());
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

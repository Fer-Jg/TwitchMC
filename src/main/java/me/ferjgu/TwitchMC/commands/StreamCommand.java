package me.ferjgu.TwitchMC.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.ferjgu.TwitchMC.Main;
import me.ferjgu.TwitchMC.objects.Streamer;
import me.ferjgu.utils.Utils;
import me.ferjgu.utils.command.CommandBuilder;
import me.ferjgu.utils.command.SubCommandBuilder;
import me.ferjgu.utils.exceptions.PlayerOfflineException;
import me.ferjgu.utils.pluginSpecific.exceptions.NoStreamerException;

public class StreamCommand extends CommandBuilder {

	public StreamCommand(Main instance, String commandName) {
		super(instance, commandName);
		this.setupCommand(new SubCommandBuilder("live", "twitchmc.command.streamer.stream") {
			@Override
			public void onCommand(CommandSender sender, Command command, String[] args) {
				super.onCommand(sender, command, args);
				if(!(sender instanceof Player)) {
					sender.sendMessage(Utils.color("Sorry console, this is only for streamers."));
					return;
				}
				Player player = (Player) sender;
				Streamer streamer = null;
				try {
					streamer = plugin.getStreamer(player);
				} catch (NoStreamerException e) {
					player.sendMessage(Utils.prefixColor(plugin.locale.get("notStreamer")));
					return;
				}
				try {
					if(!streamer.isOnline()) {
						streamer.setOnline(true);
						streamer.broadcastLive(plugin);
					}
					else {
						player.sendMessage(Utils.prefixColor(plugin.locale.get("alreadyOnline")));
						return;
					}
				} catch (PlayerOfflineException e) {e.printStackTrace();}
				player.sendMessage(Utils.prefixColor(plugin.locale.get("nowOnline")));
			}
		}, new SubCommandBuilder("off", "twitchmc.command.streamer.stream") {
			@Override
			public void onCommand(CommandSender sender, Command command, String[] args) {
				super.onCommand(sender, command, args);
				if(!(sender instanceof Player)) {
					sender.sendMessage(Utils.color("Sorry console, this is only for streamers."));
					return;
				}
				Player player = (Player) sender;
				Streamer streamer = null;
				try {
					streamer = plugin.getStreamer(player);
				} catch (NoStreamerException e) {
					player.sendMessage(Utils.prefixColor(plugin.locale.get("notStreamer")));
				}
				try {
					if(streamer.isOnline()) {
						streamer.setOnline(false);
					}
					else {
						player.sendMessage(Utils.prefixColor(plugin.locale.get("alreadyOffline")));
						return;
					}
				} catch (PlayerOfflineException e) {e.printStackTrace();}
				player.sendMessage(Utils.prefixColor(plugin.locale.get("nowOffline")));
			}
		});
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if(super.onCommand(sender, command, label, args)) return true;
		if(!(sender instanceof Player)) {
			sender.sendMessage(Utils.color("Sorry console, this is only for streamers."));
			return true;
		}
		
		return true;
		
		
	}

}

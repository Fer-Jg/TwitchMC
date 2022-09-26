package me.ferjgu.utils.pluginSpecific.exceptions;

import org.bukkit.entity.Player;

import me.ferjgu.TwitchMC.objects.Streamer;

@SuppressWarnings("serial")
public class NoStreamerException extends Exception {
	
	public NoStreamerException(Streamer streamer) {
		super("Streamer " + streamer.getUser() + " was not found.");
	}
	
	public NoStreamerException(String name) {
		super("Streamer " + name + " was not found.");
	}
	
	public NoStreamerException(Player player) {
		super("No streamer was found for player " + player.getName() + ".");
	}
}

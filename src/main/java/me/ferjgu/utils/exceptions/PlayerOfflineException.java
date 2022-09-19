package me.ferjgu.utils.exceptions;

import org.bukkit.OfflinePlayer;

@SuppressWarnings("serial")
public class PlayerOfflineException extends Exception {
	
	public PlayerOfflineException(OfflinePlayer player) {
		super("Player " + player.getName() + " is not online, cannot get Player object");
	}
	
	public PlayerOfflineException(String playerName) {
		super("Player " + playerName + " is not online, cannot get Player object");
	}

}

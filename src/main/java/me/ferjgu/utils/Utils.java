package me.ferjgu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.ferjgu.TwitchMC.Main;
import me.ferjgu.utils.exceptions.PlayerOfflineException;

public class Utils {
	
	static Random random = new Random();

	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static String prefixColor(String str) {
		return color(Main.pluginPrefix + " " + str);
	}

	public static void LogInfo(String str) {
//		Bukkit.getLogger().info(prefixColor("&b[INFO] " + str));
		Bukkit.getConsoleSender().sendMessage(prefixColor("&b[INFO] " + str));
	}

	public static void LogGood(String str) {
//		Bukkit.getLogger().fine(prefixColor("&a[OK] " + str));
		Bukkit.getConsoleSender().sendMessage(prefixColor("&a[OK] " + str));
	}

	public static void LogWarn(String str) {
		Bukkit.getLogger().warning(prefixColor("&c[!!WARN!!] " + str));
		//Bukkit.getConsoleSender().sendMessage(prefixColor("&c[!!WARN!!] " + str));
	}
	
	public static Player getOnlinePlayer(OfflinePlayer player) throws PlayerOfflineException {
		if(player.isOnline()) return (Player) player;
		throw new PlayerOfflineException(player);
	}
	
	public static String getCurrentTimeDate(boolean clean) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return clean ? format.format(new Date()) : "[" + format.format(new Date()) + "]";
	}
	
	public static String getCurrentDate(boolean clean) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return clean ? format.format(new Date()) : "[" + format.format(new Date()) + "]";
	}
}

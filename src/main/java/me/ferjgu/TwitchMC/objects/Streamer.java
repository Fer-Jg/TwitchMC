package me.ferjgu.TwitchMC.objects;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.ferjgu.TwitchMC.Main;
import me.ferjgu.utils.Utils;
import me.ferjgu.utils.exceptions.PlayerOfflineException;
import me.ferjgu.utils.pluginSpecific.Tools;
import me.ferjgu.utils.pluginSpecific.exceptions.InvalidTypeException;

public class Streamer {
	
	private String user;
	private String channel;
	private String live_message;
	private String chat_prize;
	private String sub_prize;
	private String follow_prize;
	private int message_repeats;
	// delay is stored in seconds, might be needed to transform into ticks.
	private int broadcast_delay;
	private boolean isOnline;
	
	private OfflinePlayer linkedPlayer;
	private Player onlinePlayer;
	
	@SuppressWarnings("deprecation")
	public Streamer(MemorySection data) {
		this.user = data.getString("user");
		this.channel = data.getString("channel");
		this.live_message = data.getString("liveMessage");
		this.chat_prize = data.getString("chatPrize");
		this.sub_prize = data.getString("subPrize");
		this.follow_prize = data.getString("followPrize");
		this.message_repeats = data.getInt("messageRepetition");
		this.broadcast_delay = data.getInt("repeatDelay");
		this.isOnline = false;
		
		this.linkedPlayer = Bukkit.getOfflinePlayer(this.user);
		this.onlinePlayer = null;
	}
	
	public void broadcastLive(Main pluginInstance) {
		new BukkitRunnable() {
			int i = message_repeats;
			@Override
			public void run() {
				if(i==0 || !isOnline) cancel();
				try {
					Bukkit.broadcastMessage(Utils.color(Tools.formatMessage(live_message, getOuter())));
				} catch (InvalidTypeException e) {e.printStackTrace();}
				i--;
				if(i==0 || !isOnline) cancel();
			}
		}.runTaskTimer(pluginInstance, 0, broadcast_delay * 20);
	}
	
	private Streamer getOuter() {return this;}
	
	public Player getOnlinePlayer() {return this.onlinePlayer;}
	public boolean isOnline() { return this.isOnline;}
	public void setOnline(boolean status) throws PlayerOfflineException {
		this.isOnline = status;
		this.onlinePlayer = status ? Utils.getOnlinePlayer(this.linkedPlayer) : null;
	}

	public String getUser() {return user;}
	public void setUser(String user) {this.user = user;}

	public String getLive_message() {return live_message;}
	public void setLive_message(String live_message) {this.live_message = live_message;}

	public String getChannel() {return channel;}
	public void setChannel(String channel) {this.channel = channel;}

	public String getChat_prize() {return chat_prize;}
	public void setChat_prize(String chat_prize) {this.chat_prize = chat_prize;}

	public String getFollow_prize() {return follow_prize;}
	public void setFollow_prize(String follow_prize) {this.follow_prize = follow_prize;}

	public String getSub_prize() {return sub_prize;}
	public void setSub_prize(String sub_prize) {this.sub_prize = sub_prize;}
	
}

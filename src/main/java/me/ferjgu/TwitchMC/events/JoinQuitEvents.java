package me.ferjgu.TwitchMC.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.ferjgu.TwitchMC.Main;
import me.ferjgu.utils.exceptions.PlayerOfflineException;
import me.ferjgu.utils.pluginSpecific.exceptions.NoStreamerException;

public class JoinQuitEvents implements Listener {
	
	private Main plugin;
	public JoinQuitEvents(Main instance) {
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
//	@EventHandler
//	public void onPlayerJoin(PlayerJoinEvent event) {
//		
//	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		try {
			if(plugin.getStreamer(event.getPlayer()).isOnline()){
				plugin.getStreamer(event.getPlayer()).setOnline(false);
				}
			} catch (PlayerOfflineException | NoStreamerException e) {return;}
	}

}

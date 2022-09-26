package me.ferjgu.TwitchMC;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.ITwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.helix.domain.Stream;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import me.ferjgu.TwitchMC.commands.CoreCommand;
import me.ferjgu.TwitchMC.commands.StreamCommand;
import me.ferjgu.TwitchMC.events.JoinQuitEvents;
import me.ferjgu.TwitchMC.events.TwitchEvents;
import me.ferjgu.TwitchMC.objects.Streamer;
import me.ferjgu.utils.Utils;
import me.ferjgu.utils.configs.CustomConfig;
import me.ferjgu.utils.pluginSpecific.exceptions.NoStreamerException;

public final class Main extends JavaPlugin {

    private ITwitchClient client;
    public CustomConfig mainConfig;
    public CustomConfig localeConfig;
    public HashMap<String, String> locale;
    private HashMap<String, Streamer> streams;
    public static String pluginPrefix;

    @Override
    public void onEnable() {
    	pluginPrefix = "&7[&d" + getDescription().getPrefix() + "&7]";
    	
    	loadConfigs();
        
        initTwitch();
        initCommands();
        initListeners();
    }

    @Override
    public void onDisable() {
        if (client != null) client.close();
        Utils.LogInfo("Plugin disabled.");
    }
    
    private void loadConfigs() {
    	
        mainConfig = new CustomConfig(this, "config");
        localeConfig = new CustomConfig(this, "messages");
        
        locale = new HashMap<String, String>();
        
        localeConfig.data.getKeys(true).forEach(key -> locale.put(key, (String) localeConfig.data.get(key)));
    }
    
    public void reloadPlugin() {
    	// onlineStreamers is not reloaded, it's handled through #JoinQuitEvents and #StreamCommand
    	mainConfig.reloadConfigs();
    	localeConfig.reloadConfigs();
    	if(client != null) client.close();
    	initTwitch();
    }
    
    private void initCommands() {
    	new CoreCommand(this, "twitchmc");
    	new StreamCommand(this, "stream");
    }
    
    private void initListeners() {
    	new JoinQuitEvents(this);
    }
    
    private void initTwitch() {
    	reloadStreamers();

        // Build credential when possible
    	client = null;
        String token = mainConfig.data.getString("oauthToken");
        OAuth2Credential credential = StringUtils.isNotBlank(token) ? new OAuth2Credential("twitch", token) : null;
        if(credential == null) {
        	Utils.LogWarn("Blank token received from config, plugin won't work until reloaded...");
        }

        // Build TwitchClient
        client = TwitchClientBuilder.builder()
            .withClientId(mainConfig.data.getString("clientID"))
            .withClientSecret(mainConfig.data.getString("clientSecret"))
            .withEnableChat(true)
            .withChatAccount(credential)
            .withEnableHelix(true)
            .withDefaultAuthToken(credential)
            .build();

        // Load the channels from each streamer in the configuration
        Set<String> channels = new HashSet<String>();
        if(!streams.isEmpty()) channels = streams.keySet();
        else {
        	Utils.LogWarn("There were no streamers loaded so no listeners were registered.");
        	return;
        }

        // Join the twitch chats of these channels and enable stream/follow events        
        if (!channels.isEmpty()) {
            channels.forEach(name -> {
            	client.getChat().joinChannel(name);
            	Utils.LogGood("Listening to events from channel " + name);
            });
            try {
            	client.getClientHelper().enableStreamEventListener(channels);
            	client.getClientHelper().enableFollowEventListener(channels);
            } catch(HystrixRuntimeException e) {
            	Utils.LogWarn("Found invalid token in configuration, the plugin won't register any events "
            			+ "until it is reloaded with a valid token.");
            	client.close();
            	client = null;
            	return;
            }
        }

        // Register event listeners
        client.getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new TwitchEvents(this));
    }
    
    public void reloadStreamers() {
        ConfigurationSection section = mainConfig.data.getConfigurationSection("streamers");
    	
        Set<String> streamerData = section.getKeys(false);
        if(streamerData == null) {
        	Utils.LogWarn("Failed to load streamers data.");
        	return;
        }
        
        Streamer aux = null;
        
        streams = new HashMap<String, Streamer>();
        
        for (String index : streamerData) {
        	aux = new Streamer((MemorySection) section.get(index));
        	Utils.LogGood("Loaded streamer: " + aux.getUser() + " | channel: " + aux.getChannel());
        	streams.put(aux.getChannel(), aux);
        }
    }
    
    public Streamer getStreamer(Stream stream) {return streams.get(stream.getUserName());}
    public Streamer getStreamer(String channel) {return streams.get(channel);}
    public Streamer getStreamer(Player player) throws NoStreamerException {
    	for(Streamer s : getStreamers()) { if(s.getUser().equalsIgnoreCase(player.getName())) return s; }
    	throw new NoStreamerException(player.getName());
    }
    
    public ITwitchClient getTwitchClient() {return this.client;}
    
    public Collection<Streamer> getStreamers(){return this.streams.values();}
}

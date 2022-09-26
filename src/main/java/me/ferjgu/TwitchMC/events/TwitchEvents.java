package me.ferjgu.TwitchMC.events;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.helix.domain.Stream;

import me.ferjgu.TwitchMC.Main;
import me.ferjgu.TwitchMC.objects.Streamer;
import me.ferjgu.utils.Utils;
import me.ferjgu.utils.pluginSpecific.Tools;
import me.ferjgu.utils.pluginSpecific.exceptions.InvalidTypeException;

public class TwitchEvents {

    private final Main plugin;

    public TwitchEvents(Main plugin) {
        this.plugin = plugin;
    }
    
    @EventSubscriber
    public void onChat(ChannelMessageEvent event) {
    	Streamer streamer = plugin.getStreamer(event.getChannel().getName());
    	if(streamer.isOnline()) {
    		try {
    			String message = Tools.formatMessage(plugin.locale.get("chatFormat"), event);
    			streamer.getOnlinePlayer().sendMessage(Utils.color(message));
    		} catch (InvalidTypeException e) {
				e.printStackTrace();
			}
    	}
    }

    @SuppressWarnings("unchecked")
	@EventSubscriber
    public void onStreamUp(ChannelGoLiveEvent event) {
        Stream stream = event.getStream();
        String title = stream.getTitle();
        // Don't even bother to continue if the title doesn't include the required strings 
        for(String s : (List<String>) plugin.mainConfig.data.getList("streamTitleMustContain"))
        	if(!title.toLowerCase().contains(s.toLowerCase())) return;
        
        Streamer streamer = plugin.getStreamer(stream);
        String message = streamer.getLive_message();
        
        if(StringUtils.isNotBlank(message))
			try {
				broadcast(Tools.formatAll(message, stream, streamer));
			} catch (InvalidTypeException e) {
				e.printStackTrace();
			}
    }
//
//    @EventSubscriber
//    public void onStreamDown(ChannelGoOfflineEvent event) {
//        broadcast(String.format("[Twitch] %s has stopped streaming.", event.getChannel().getName()));
//    }
//
//    @EventSubscriber
//    public void onFollow(FollowEvent event) {
//        broadcast(String.format("[Twitch] %s just followed %s!", event.getUser().getName(), event.getChannel().getName()));
//    }
//
//    @EventSubscriber
//    public void onCheer(CheerEvent event) {
//        if (event.getBits() >= 500)
//            broadcast(String.format("[Twitch] %s just cheered %d bits for %s!", event.getUser().getName(), event.getBits(), event.getChannel().getName()));
//    }
//
//    @EventSubscriber
//    public void onSub(SubscriptionEvent event) {
//        if (!event.getGifted())
//            broadcast(String.format("[Twitch] %s just subscribed to %s for %d months", event.getUser().getName(), event.getChannel().getName(), event.getMonths()));
//    }
//
//    @EventSubscriber
//    public void onSubMysteryGift(GiftSubscriptionsEvent event) {
//        broadcast(String.format("[Twitch] Thank you %s for gifting %d subs to %s", event.getUser().getName(), event.getCount(), event.getChannel().getName()));
//
//        // Create a firework at spawn for a large gift sub event
//        // Note: these EventSubscriber's operate on a separate thread so we need to use Bukkit's scheduler to run this on the main thread
//        if (event.getCount() >= 25) {
//            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
//                World world = plugin.getServer().getWorlds().get(0);
//                Location spawnLocation = world.getSpawnLocation();
//                if (world.isChunkLoaded(spawnLocation.getChunk())) {
//                    world.spawn(spawnLocation, Firework.class, fw -> {
//                        FireworkMeta meta = fw.getFireworkMeta();
//                        meta.setPower(Math.min(event.getCount(), 64));
//                        meta.addEffect(
//                            FireworkEffect.builder()
//                                .with(FireworkEffect.Type.STAR)
//                                .flicker(event.getCount() >= 50)
//                                .trail(event.getCount() >= 100)
//                                .withColor(Color.FUCHSIA)
//                                .withFade(Color.PURPLE)
//                                .build()
//                        );
//                        fw.setFireworkMeta(meta);
//                    });
//                }
//            });
//        }
//    }

    private void broadcast(String message) {
        this.plugin.getServer().broadcastMessage(message);
    }

}

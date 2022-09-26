package me.ferjgu.utils.pluginSpecific;

import java.util.HashMap;
import java.util.Map;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.helix.domain.Stream;

import me.ferjgu.TwitchMC.objects.Streamer;
import me.ferjgu.utils.Utils;
import me.ferjgu.utils.pluginSpecific.exceptions.InvalidTypeException;

public class Tools {

	public static String formatMessage(String original, Map<String, String> data) {
		String output = original;
		if(data.isEmpty()) Utils.LogWarn("empty string data.");
		for(String key : data.keySet()) {output = output.replace(key, data.get(key));}
		return output;
	}
	public static String formatMessage(String original, Object object) throws InvalidTypeException {
		return formatMessage(original, getObjectData(object));
	}
	
	public static String formatAll(String original, Object ... objects) throws InvalidTypeException {
		String output = original;
		for(Object object : objects) output = formatMessage(output, object);
		return output;
	}
	
	public static Map<String, String> getObjectData(Object object) throws InvalidTypeException{
		Map<String, String> data = new HashMap<String, String>();
		if(validClassObject(object, Streamer.class)) {
			Streamer morphed = (Streamer) object;
			data.put("{channel}", morphed.getChannel());
			data.put("{streamer}", morphed.getUser());
			data.put("{link}", "https://twitch.tv/" + morphed.getChannel());
		}
		else if (validClassObject(object, Stream.class)){
			Stream morphed = (Stream) object;
			data.put("{game}", morphed.getGameName());
			data.put("{title}", morphed.getTitle());
			data.put("{channelname}", morphed.getUserName());
			data.put("{link}", "twitch.tv/"+morphed.getUserName());
			data.put("{viewers}", String.valueOf(morphed.getViewerCount()));
			data.put("{uptime}", morphed.getUptime().toString());
		}
		else if (validClassObject(object, ChannelMessageEvent.class)) {
			ChannelMessageEvent morphed = (ChannelMessageEvent) object;
			data.put("{channel}", morphed.getChannel().getName());
			data.put("{user}", morphed.getUser().getName());
			data.put("{message}", morphed.getMessage());
		}
//		else if (object.getClass().equals(CheerEvent.class)) {
//			CheerEvent morphed = (CheerEvent) o;
//			data.put("{bits}", String.valueOf(morphed.getBits()));
//		}
		else {	throw new InvalidTypeException(object);	}
		return data;
	}
	
	private static boolean validClassObject(Object object, Class<?> desiredClass) {
		return object.getClass().getTypeName().equalsIgnoreCase(desiredClass.getTypeName());
	}
}

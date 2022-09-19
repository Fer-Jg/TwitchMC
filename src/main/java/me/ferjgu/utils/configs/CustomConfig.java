package me.ferjgu.utils.configs;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

import me.ferjgu.TwitchMC.Main;

public class CustomConfig {
	
	public Main plugin;
	public ConfigFileManager file;
	public FileConfiguration data;
	private HashMap<String, Object> defaults = null;

	
	/**
	 * Initializes the configuration file WITHOUT ANY default values.
	 * @param instance
	 * @param fileName
	 */
	public CustomConfig(Main instance, String fileName) {
		this.plugin = instance;
		this.file = new ConfigFileManager(this.plugin, fileName);
		this.data = file.getCustomConfig();
	}
	
	/**
	 * Initializes the configuration file and makes the default values.
	 * @param instance
	 * @param fileName
	 * @param defaultValues
	 */
	public CustomConfig(Main instance, String fileName, HashMap<String, Object> defaultValues) {
		this.plugin = instance;
		this.file = new ConfigFileManager(this.plugin, fileName);
		this.data = file.getCustomConfig();
		this.defaults = defaultValues;
		this.setDefaults(defaults);
	}
	
	public void reloadConfigs() {
		this.file.reloadConfig();
		this.data = file.getCustomConfig();
	}
	
	private void setDefaults(HashMap<String, Object> values) {
		for(String path : this.defaults.keySet()) {
			this.data.addDefault(path, this.defaults.get(path));
		}
	}
}

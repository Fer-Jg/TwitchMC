package me.ferjgu.utils.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.ferjgu.TwitchMC.Main;
import me.ferjgu.utils.Utils;

public class ConfigFileManager {

	private File customConfigFile;
    private FileConfiguration customConfig;
    private String fileName;

	static Main plugin;
	public ConfigFileManager(Main instance, String fileName) {
		this.fileName = fileName + ".yml";
		plugin = instance;
		createCustomConfig();
	}

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(plugin.getDataFolder(), fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            try {
                plugin.saveResource(fileName, false);            	
            } catch(IllegalArgumentException e) {
            	try {
					customConfigFile.createNewFile();
					Utils.LogInfo("There was no file " + fileName + " found in jar's resources, created new empty file.");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
         }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        this.createCustomConfig();
    }

    public void saveConfig() {
    	try {
			this.customConfig.save(this.customConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}

package me.ferjgu.utils.eventLog;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import me.ferjgu.TwitchMC.Main;
import me.ferjgu.utils.Utils;
import me.ferjgu.utils.configs.CustomConfig;

public class IncidentLog {
	
	private Main plugin;
	private String logType;
	private CustomConfig configFile;
	private List<String> logs;
	
	@SuppressWarnings("unchecked")
	public IncidentLog(Main instance, String logTypeFormat) {
		this.plugin = instance;
		this.logType = logTypeFormat;
		this.configFile = new CustomConfig(instance, "Logs/"+logTypeFormat+"-" + Utils.getCurrentDate(true));
		this.logs = (List<String>) configFile.data.getList("History");
		
		if(this.logs == null) {
			this.logs = new ArrayList<String>();
			this.addLog("Created new " + this.logType + " logs.");
		}	else	this.addLog("Loaded logs.");
		
		this.startSaveLoop();
	}
	
	public void saveLogs() {
		this.configFile.file.saveConfig();
			Utils.LogGood(" &dLogs for " + this.logType +  " saved.");
	}
	
	private void startSaveLoop(){
		Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
			this.saveLogs();
		}, 20L, 20*60*5);
	}
	
	public String addLog(String content) {
		String output = Utils.getCurrentTimeDate(false) + " " + content;
		this.logs.add(output);
		this.configFile.data.set("History", this.logs);
		return output;
	}
}

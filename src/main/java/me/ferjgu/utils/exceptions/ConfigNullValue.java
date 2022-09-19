package me.ferjgu.utils.exceptions;

@SuppressWarnings("serial")
public class ConfigNullValue extends Exception {
	
	public ConfigNullValue() {
		super("");
	}
	
	public ConfigNullValue(String path) {
		super("The path " + path + " has a null value.");
	}

}

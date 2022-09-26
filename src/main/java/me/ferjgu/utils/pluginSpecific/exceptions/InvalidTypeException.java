package me.ferjgu.utils.pluginSpecific.exceptions;

@SuppressWarnings("serial")
public class InvalidTypeException extends Exception {
	
	
	public InvalidTypeException(Object object) {
		super(object.getClass().getName() + " is not a valid type for the operation.");
	}
}

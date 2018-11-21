package Exceptions;

public class ButtonTypeSwitchException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message ;
	public ButtonTypeSwitchException(String ExceptionLocation) {
		message = "Exception in "+ ExceptionLocation;
	}
}

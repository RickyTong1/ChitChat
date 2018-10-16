package Exceptions;

import Constants.Initialization;

public class InitializationException extends Exception {
	String message;

	public InitializationException(int InitializationMode, int ID/* ¶ÔÏóµÄID */) {
		switch (InitializationMode) {
		case Initialization.MESSAGE:
			message = "Message" + ID + " initializing error.";
			break;
		case Initialization.CONTS:
			message = "Conts" + ID + " initializing error.";
			break;
		case Initialization.CONTACTS:
			message = "Contacts" + ID + " initializing error.";
			break;
		}
	}

}

package Exceptions;

public class FileReadException extends Exception{
	String message = null;
	FileReadException(String s){
		message = s;
	}
	
}

package Restores;
import java.io.*;

public class KeyKeep implements Serializable {
	public String id;
	public char[] keySecured;
	protected KeyKeep(String id,char[] keySecured) {
		this.id = id;
		this.keySecured = keySecured;
	}
}


package Restores;

import java.util.Vector;

public class KeyKeepOperate {

	public static KeyKeep pack(String ID, String Key) {//º”√‹
		String id = ID;
		char[] keyChar = Key.toCharArray();
		char[] keySecured = new char[keyChar.length];
		for (int i = 0; i < keyChar.length; i++)
			keySecured[i] = (char) (keyChar[i] ^ id.toCharArray()[0]);
		return new KeyKeep(id, keySecured);
	}

	public static Vector<KeyKeep> unpack(Vector<KeyKeep> keys) {//Ω‚√‹
		Vector<KeyKeep> upKey = new Vector<KeyKeep>();
		for(KeyKeep i : keys) {
			String id = i.id;
			String key;
			char[] keyChar = new char[i.keySecured.length];
			for(int j = 0; j < i.keySecured.length; j++)
				keyChar[j] = (char) (i.keySecured[j]  ^ id.toCharArray()[0]);
			upKey.add(new KeyKeep(id, keyChar));
		}
		return upKey;
	}
}

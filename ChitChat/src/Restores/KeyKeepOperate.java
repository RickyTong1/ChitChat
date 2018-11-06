package Restores;

public class KeyKeepOperate {

	public static KeyKeep pack(String ID, String Key) {//º”√‹
		String id = ID;
		char[] keyChar = Key.toCharArray();
		char[] keySecured = new char[keyChar.length];
		for (int i = 0; i < keyChar.length; i++)
			keySecured[i] = (char) (keyChar[i] ^ id.toCharArray()[0]);
		return new KeyKeep(id, keySecured);
	}

	public static KeyKeep unpack(KeyKeep keys) {//Ω‚√‹
		String id = keys.id;
		String key;
		char[] keyChar = new char[keys.keySecured.length];
		for(int i = 0; i < keys.keySecured.length; i++)
			keyChar[i] = (char) (keys.keySecured[i]  ^ id.toCharArray()[0]);
		return new KeyKeep(id, keyChar);
	}
}

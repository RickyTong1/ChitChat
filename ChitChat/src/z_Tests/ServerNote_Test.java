package z_Tests;

import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlob.File;
import CComponents.MessageBlobType;
import Client.MainWindow;
import utils.ClientTranslation;

public class ServerNote_Test {

	public static void main(String...strings) {
		new MainWindow(40);
		MessageBlob e = new MessageBlob();
		e.type = MessageBlobType.SELF_FILE;
		e.answer = MessageAnswerType.POSITIVE;
		int len = 20;
		e.filelist = new MessageBlob.File[len];

		for(int i = 0 ; i < len; i ++) {
			e.filelist[i] = e.new File();
			e.filelist[i].capacity = 2314;
			e.filelist[i].id = 44;
			e.nickname = "Test";
			e.remark = "test";
		}
		ClientTranslation.typeTrans(e);
	}
}

package z_Tests;

import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlobType;
import CComponents.MessageBlob.File;
import Client.MainWindow;
import utils.ClientTranslation;

public class Message_Test {

	public static void main(String...strings) {
		new MainWindow(80);
		MessageBlob e = new MessageBlob();
		e.type = MessageBlobType.SELF_FILE;
		e.answer = MessageAnswerType.POSITIVE;
		int len = 10;
		e.filelist = new MessageBlob.File[len];

		for(int i = 0 ; i < len; i ++) {
			e.filelist[i] = e.new File();
			e.filelist[i].capacity = 2314;
			e.filelist[i].id = i;
			e.nickname = "Test";
			e.remark = "test";
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		ClientTranslation.typeTrans(e);
	}
}

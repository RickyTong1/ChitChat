package z_Tests;

import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlobType;
import Client.MainWindow;
import CComponents.MessageBlob.File;
import utils.ClientTranslation;

public class Files_Onaji_Test {
	
	public static void main(String[] a) {
		new MainWindow(0);
		MessageBlob e = new MessageBlob();
		e.type = MessageBlobType.SELF_FILE;
		e.answer = MessageAnswerType.POSITIVE;
		int len = 3;
		e.filelist = new MessageBlob.File[len];

		for(int i = 0 ; i < len; i ++) {
			e.filelist[i] = e.new File();
			e.filelist[i].capacity = 2314;
			e.filelist[i].id = i/2+3;
			e.filelist[i].nickname = "Test";
	//		e.filelist[i].remark = "test";
		}
		ClientTranslation.typeTrans(e);
	}

}

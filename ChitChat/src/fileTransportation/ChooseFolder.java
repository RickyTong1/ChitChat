package fileTransportation;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Constants.SocketConstants;

/*该类用于保存设备存储空间，并将接收到的文件保存到该位置*/
/*目前有个问题，就是弹出文件浏览器时如果直接点关闭窗口会报错，因为无法获得路径*/
public class ChooseFolder {
	public static String savePath;// 保存路径

	public ChooseFolder() {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.showDialog(new JLabel(), "选择");
		File file = jfc.getSelectedFile();

		if (file != null) {
			savePath = file.getAbsolutePath();
			System.out.println("文件:" + file.getAbsolutePath());
			System.out.println(jfc.getSelectedFile().getName());
		}

	}

	public static void save() {
		if (savePath == null) {
			JOptionPane.showMessageDialog(null, "发送文件不能为空");
		} else {
			ReceiveFile se = new ReceiveFile(savePath, SocketConstants.FILE_GRN_PORT);
			se.start();// 启动线程
		}
	}
}

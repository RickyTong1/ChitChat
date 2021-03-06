package Property;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class Property {

	static Properties props;
	public static String NATIVE_IP;
	public static String SERVER_IP;
	public static String IMAGE_ONLINE_URL = "image\\Online.png";
	public static String IMAGE_OFFLINE_URL = "image\\Offline.png";
	public static String USER_INFO_URL = "data\\user_info.bat";
	static {
		try {
			props = new Properties();
			FileInputStream fis;
			try {

				fis = new FileInputStream("property\\properties.properties");
				props.load(fis);
				fis.close();// 关闭流
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			props.setProperty( InetAddress.getLocalHost().getHostAddress(),"OPP");
			FileOutputStream fos = new FileOutputStream("property\\properties.properties");   
         // 将Properties集合保存到流中   
         props.store(fos, "Copyright (c) ChitChat Team@RickyTong @MattWong @Junsheng Wong @Zhiwei Li");   
         fos.close();// 关闭流 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   
	}
	        // 文件输出流  
	static {// 提取property
		
		try {
			String tail = props.getProperty(InetAddress.getLocalHost().getHostAddress());
			System.out.println(tail);
			
			NATIVE_IP = InetAddress.getLocalHost().getHostAddress();
			if(NATIVE_IP.split(".", 2)[0].equals("172")) {//切分出本地IP的第一段
				tail = "AHNU";
			} else {
				tail = "OPP";
			}
			SERVER_IP = props.getProperty("SERVER_IP_" + tail);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 

	}
}

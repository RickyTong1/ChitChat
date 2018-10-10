package Server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * ��ȡ����ʱ��
 * 
 * @author ����
 * @date 2018��9��29��
 */
public class GetNetworkTime {
//	String webUrl = "http://www.ntsc.ac.cn";//�й���ѧԺ������ʱ����
	public static String getWebsiteDatetime(String webUrl) {
		try {
			URL url = new URL(webUrl);//��ȡ��Դ����
			URLConnection urlConnection = url.openConnection();//�������Ӷ��� 
			urlConnection.connect();//��������
			long ld = urlConnection.getDate();
			Date date = new Date(ld);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss",Locale.CHINA);// �������ʱ��
			return dateFormat.format(date);
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

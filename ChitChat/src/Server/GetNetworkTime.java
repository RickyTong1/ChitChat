package Server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 读取网络时间
 * 
 * @author 羽恋
 * @date 2018年9月29日
 */
public class GetNetworkTime {
//	String webUrl = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
	public static String getWebsiteDatetime(String webUrl) {
		try {
			URL url = new URL(webUrl);//获取资源对象
			URLConnection urlConnection = url.openConnection();//生成链接对象 
			urlConnection.connect();//发出链接
			long ld = urlConnection.getDate();
			Date date = new Date(ld);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss",Locale.CHINA);// 输出北京时间
			return dateFormat.format(date);
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

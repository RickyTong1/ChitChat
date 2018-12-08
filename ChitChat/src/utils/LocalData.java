package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalData {
	public static final int NOW_YEAR = Integer.parseInt(getLocalYearTime());//��ǰ���
	public static final int NOW_MONTH = Integer.parseInt(getLocalMonthTime());//��ǰ�·�
	public static final int NOW_DAY = Integer.parseInt(getLocalDayTime());//��ǰ�շ�
	/*��ȡ����ʱ��(���)*/
	public static String getLocalYearTime() {
		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY");
		return dateFormat.format(date);
	}
	/*��ȡ����ʱ��(�·�)*/
	public static String getLocalMonthTime() {
		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		return dateFormat.format(date);
	}
	/*��ȡ����ʱ��(��)*/
	public static String getLocalDayTime() {
		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		return dateFormat.format(date);
	}
}

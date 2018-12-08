package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalData {
	public static final int NOW_YEAR = Integer.parseInt(getLocalYearTime());//当前年份
	public static final int NOW_MONTH = Integer.parseInt(getLocalMonthTime());//当前月份
	public static final int NOW_DAY = Integer.parseInt(getLocalDayTime());//当前日份
	/*获取本地时间(年份)*/
	public static String getLocalYearTime() {
		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY");
		return dateFormat.format(date);
	}
	/*获取本地时间(月份)*/
	public static String getLocalMonthTime() {
		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		return dateFormat.format(date);
	}
	/*获取本地时间(日)*/
	public static String getLocalDayTime() {
		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		return dateFormat.format(date);
	}
}

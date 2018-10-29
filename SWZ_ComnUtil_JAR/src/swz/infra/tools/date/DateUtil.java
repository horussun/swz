package swz.infra.tools.date;

import java.sql.Timestamp;
import java.text.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {

	// logger
	private static final String sourceClass = DateUtil.class.getName();
	// private static final Logger logger = Logger.getLogger(sourceClass);

	private static final String DataFormat_2 = "yyyy-MM-dd";
	private static final String DataFormat_1 = "yyyy-MM-dd hh:mm:ss";
	private static final String DataFormat_3 = "yyyy-MM-dd hh:mm:ss.SSS";
	private static final String DataFormat_4 = "hh:mm:ss.SSS";

	private static SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
	private static SimpleDateFormat sdf_us = new SimpleDateFormat("", Locale.US);
	private static DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.SIMPLIFIED_CHINESE);

	private static long previousTime = -1; // the prevous time record in last
											// turn
	private static long beginningTime = -1; // the prevous time record in last
											// turn

	// common format:
	// "yyyy-MM-dd HH:mm:ss.SSS"

	/**
	 * 取得日期型变量的中文标准格式的日期字符串
	 * 
	 * @param d
	 *            Date
	 * @return String
	 */
	public static final String format(Date d) {
		return df.format(d);
	}

	/**
	 * 取得long型日期的中文标准格式的日期字符串
	 * 
	 * @param d
	 *            long
	 * @return String
	 */
	public static final String format(long d) {
		return df.format((new Date(d)));
	}

	/**
	 * 取得long型日期的中文标准格式的日期字符串
	 * 
	 * @param d
	 *            long
	 * @return String
	 */
	public static final String format(long d, String pattern) {
		sdf.applyPattern(pattern);
		return sdf.format((new Date(d)));
	}

	/**
	 * 取得String型日期的中文标准格式的日期字符串
	 * 
	 * @param d
	 *            String
	 * @return String
	 */
	public static final String format(String d) {
		try {
			return df.format(df.parse(d));
		} catch (ParseException ex) {
			return d;
		}
	}

	/**
	 * 根据给定日期d，及日期显示格式ft，返回对应的日期字符串结果；
	 * 
	 * @param d
	 *            Date
	 * @param ft
	 *            String;
	 * @return String ex:ex:getDateFormat("", "yyyy-M-d");
	 */
	public static final String getDateFormat(Date d, String ft) {
		sdf.applyPattern(ft);
		return sdf.format(d);
	}

	public static final String getDateFormat(String sDate, String ft) {
		sdf.applyPattern(ft);
		if (sDate == null || sDate.equalsIgnoreCase("")) {
			return sdf.format(new Date());
		} else {
			return sdf.format(new Date(sDate));
		}

	}

	/**
	 * 根据long型日期及日期显示格式返回日期字符串
	 * 
	 * @param d
	 *            long
	 * @param ft
	 *            String
	 * @return String
	 */
	public static final String getDateFormat(long d, String ft) {
		sdf.applyPattern(ft);
		return sdf.format(new Date(d));
	}

	/**
	 * get the difference between d in differrent turn;
	 * 
	 * @param d1
	 * @param d2
	 * @param diffFormat
	 *            : Hour:Minutes:Second.Millionsecond
	 * @param compareFlag
	 *            : "": compare with previous time; "withBeginTime": compare
	 *            with beginning time;
	 * @return
	 */
	public static final String elapose(long currTime, String compareFlag) {
		String result = "";
		long diff = 0;
		String hour = "", minute = "", second = "", ms = "";

		if (compareFlag.equalsIgnoreCase("withBeginTime")) {
			diff = currTime - beginningTime;
		} else {
			diff = currTime - previousTime;
			;
		}

		if (diff == 0) {
			result = "0ms";
		} else if (diff > 0) {
			ms = String.valueOf(diff % 1000);
			result = result + ms + "ms";
			diff = diff / 1000;
			if (diff > 0) {
				second = String.valueOf(diff % 60);
				result = second + "s." + result;
				diff = diff / 60;
				if (diff > 0) {
					minute = String.valueOf(diff % 60);
					result = minute + "m:" + result;
					diff = diff / 60;
					if (diff > 0) {
						hour = String.valueOf(diff % 60);
						result = hour + "h:" + result;
						diff = diff / 60;
						if (diff > 0) {
							result = hour + "O:" + result;
						}
					}
				}
			}
		}
		// if(compareFlag.equalsIgnoreCase("withBeginTime")){
		// System.out.println("DateUtil.elapose [beginningTime:" +
		// format(beginningTime,DataFormat_4) + " CurrTime:" +
		// format(currTime,DataFormat_4) + " Diff:" +
		// (currTime-beginningTime)+" ms]" );
		// }
		// else{
		// System.out.println("DateUtil.elapose [previousTime:" +
		// format(previousTime,DataFormat_4) + " CurrTime:" +
		// format(currTime,DataFormat_4) + " Diff:" +
		// (currTime-previousTime)+" ms]" );
		// }
		previousTime = currTime;
		return result;
	}

	public static long getBeginningTime() {
		return beginningTime;
	}

	public static void setBeginningTime(long beginningTime) {
		System.out.println("Beginning time is: " + beginningTime);
		DateUtil.beginningTime = beginningTime;
		DateUtil.previousTime = beginningTime;
	}

	/**
	 * 根据给定日期串d，及格式描述，取得日期型对象
	 * 
	 * @param d
	 *            String
	 * @param pattern
	 *            String
	 * @return Date
	 */
	public static Date getDate(String d, String pattern) {
		try {
			if (pattern == null) {

			} else {
				sdf.applyPattern(pattern);
			}
			return sdf.parse(d);
		} catch (ParseException ex) {
			System.out.println("=====");
			System.out.println(d);
			System.out.println(pattern);
			System.out.println(ex);
			return new Date(System.currentTimeMillis());
		}
	}

	/**
	 * method 将字符串类型的日期(like "2005-8-18")转换为一个Date（java.sql.Date）
	 * 
	 * @param dateString
	 *            需要转换为Date的字符串
	 * @return dataTime Date
	 */
	public final static Date string2Date(String dateString) throws java.lang.Exception {
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat(DataFormat_2, Locale.ENGLISH);
		dateFormat.setLenient(false);
		java.util.Date timeDate = dateFormat.parse(dateString);// util类型
		return timeDate;
	}

	/**
	 * method 将字符串类型的日期（"2005-8-18"）转换为一个timestamp（时间戳记java.sql.Timestamp）
	 * 
	 * @param dateString
	 *            需要转换为timestamp的字符串
	 * @return dataTime timestamp
	 */
	public static java.sql.Timestamp string2Timestamp(String dateString) throws java.text.ParseException {
		Timestamp ts = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DataFormat_1);
		// String time = sdf.format(new Date());
		// System.out.println("time: " + time);
		// Timestamp ts = Timestamp.valueOf(time);
		// System.out.println("test: " + ts.toLocaleString());

		// String time = sdf.format(new Date());
		// Date date = sdf.parse("2010-10-08 00:00:00");
		Date date = sdf.parse(dateString + "00:00:00");
		ts = Timestamp.valueOf(date.toLocaleString());
		// System.out.println("test: " + ts.toLocaleString());
		return ts;
	}

	public static String Timestamp2String(Timestamp tm) throws java.text.ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
		// Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		String str = df.format(tm);
		return str;
	}

	/**
	 * 获取时间戳
	 * 
	 * @see <pre>
	 * <b>Modifier:</b>孙文祥
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d
	 *            要获取其时间戳的日期值
	 * @return 时间戳
	 */
	public static final String getTimeStamp(Date d) {
		sdf.applyPattern("yyyyMMddHHmmss");
		return sdf.format(d);
	}

	/**
	 * 获取时间戳
	 * 
	 * @see <pre>
	 * <b>Modifier:</b>孙文祥
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d
	 *            要获取其时间戳的日期秒数值
	 * @return 时间戳
	 */
	public static final String getTimeStamp(long d) {
		return getTimeStamp(new Date(d));
	}

	/**
	 * 获取年月日
	 * 
	 * @see <pre>
	 * <b>Modifier:</b>孙文祥
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d
	 *            要获取其年月日的日期值
	 * @return 年月日
	 */
	public static final String getDate(Date d) {
		sdf.applyPattern(DataFormat_2);
		return sdf.format(d);
	}

	/**
	 * 获取年月日
	 * 
	 * @see <pre>
	 * <b>Modifier:</b>孙文祥
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d
	 *            要获取其年月日的日期值
	 * @return 年月日
	 */
	public static final String getZhDate(long d) {
		sdf.applyPattern("yyyy年MM月dd日");
		Date s = new Date(d);
		return sdf.format(s);
	}

	/**
	 * 获取年月日
	 * 
	 * @see <pre>
	 * <b>Modifier:</b>孙文祥
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d
	 *            要获取其年月日的日期秒数值
	 * @return 年月日
	 */
	public static final String getDate(long d) {
		return getDate(new Date(d));
	}

	/**
	 * 获取年月日时分秒
	 * 
	 * @see <pre>
	 * <b>Modifier:</b>孙文祥
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d
	 *            要获取其年月日时分秒的日期值
	 * @return 年月日时分秒
	 */
	public static final String getDateTime(Date d) {
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}

	/**
	 * 获取年月日时分秒
	 * 
	 * @see <pre>
	 * <b>Modifier:</b>孙文祥
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d
	 *            要获取其年月日时分秒的日期秒数值
	 * @return 年月日时分秒
	 */
	public static final String getDateTime(long d) {
		return getDateTime(new Date(d));
	}

	/**
	 * 获取日期的周信息
	 * 
	 * @see <pre>
	 * <b>Modifier:</b>孙文祥
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d
	 *            要获取其周信息的日期值
	 * @return 周信息
	 */
	public static final String getWeek(Date d) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayOfWeek - 1];
	}

	/**
	 * 获取日期的周信息
	 * 
	 * @see <pre>
	 * <b>Modifier:</b>孙文祥
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d
	 *            要获取其周信息的日期秒数值
	 * @return 周信息
	 */
	public static final String getWeek(long d) {
		return getWeek(new Date(d));
	}

	public static final String modify(String date, String timeUnit, int amt, String pattern) {

		String result = "";
		try {
			Calendar calendar = Calendar.getInstance();
			Date d = getDate(date, pattern);
			calendar.setTime(d);
			char unit = 'Y';

			if (StringUtils.isNotBlank(timeUnit) && timeUnit.length() == 1) {
				unit = timeUnit.toCharArray()[0];
			}

			switch (unit) {
			case 'Y':
				calendar.add(Calendar.YEAR, amt);
				break;
			case 'M':
				calendar.add(Calendar.MONTH, amt);
				break;
			case 'D':
				calendar.add(Calendar.DAY_OF_MONTH, amt);
				break;
			default:
				;
			}
			result = getDateFormat(calendar.getTime(), pattern);

		} catch (Exception e) {

		}
		return result;
	}

	public static String getRadomS() {
		String stime = getDateFormat(new java.util.Date(), "yyyyMMddHHmmssSSSSSS");
		String sNum = String.valueOf(Math.abs(new java.util.Random().nextInt()) % 100000);
		return stime + sNum;
	}

	public static int getNowHH() {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("hh");
		return new Integer(getDateFormat(new java.util.Date(), "HH")).intValue();
	}

	// 测试用
	public static void main(String[] args) {
	}
}

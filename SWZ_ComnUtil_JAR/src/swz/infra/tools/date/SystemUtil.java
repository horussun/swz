package swz.infra.tools.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Random;
/**
 * <pre>
 * designer
 * Author ������
 * Date 2007-03-16
 * description ϵͳ����
 * </pre>
 */
public class SystemUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("",
            Locale.SIMPLIFIED_CHINESE);
    private static HashMap m = null;
    public static HashMap coms = new HashMap();
    public static String openCommands="";
    private static Random random = new Random(1);
    
//    static {
//        init();
//    }

    
	/**
	 * ��ʼ�������ļ���Ϣ
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 */
//    public static void init() {
//        m = new HashMap();
//        try {
//            ResourceBundle bundle = ResourceBundle.getBundle(
//                    "mike.util.system");
//            Enumeration keys = bundle.getKeys();
//            while (keys.hasMoreElements()) {
//                String key = (String) keys.nextElement();
//                String val = bundle.getString(key);
//                m.put(key, val);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

	/**
	 * ��ȡ������Ϣ
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param key ���Լ�
	 * @return ����ֵ
	 */
    public static String getProperty(String key) {
        String val = (String) m.get(key);
        if (val == null) {
            val = "";
        }
        return val;
    }

	/**
	 * ��ȡʱ���
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d Ҫ��ȡ��ʱ���������ֵ
	 * @return ʱ���
	 */
    public static final String getTimeStamp(Date d) {
        sdf.applyPattern("yyyyMMddHHmmss");
        return sdf.format(d);
    }

	/**
	 * ��ȡʱ���
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d Ҫ��ȡ��ʱ�������������ֵ
	 * @return ʱ���
	 */
    public static final String getTimeStamp(long d) {
        return getTimeStamp(new Date(d));
    }

	/**
	 * ��ȡ������
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d Ҫ��ȡ�������յ�����ֵ
	 * @return ������
	 */
    public static final String getDate(Date d) {
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(d);
    }
    /**
	 * ��ȡ������
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d Ҫ��ȡ�������յ�����ֵ
	 * @return ������
	 */
    public static final String getZhDate(long d) {
        sdf.applyPattern("yyyy��MM��dd��");
        Date s = new Date(d);
        return sdf.format(s);
    }

	/**
	 * ��ȡ������
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d Ҫ��ȡ�������յ���������ֵ
	 * @return ������
	 */
    public static final String getDate(long d) {
        return getDate(new Date(d));
    }

	/**
	 * ��ȡ������ʱ����
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d Ҫ��ȡ��������ʱ���������ֵ
	 * @return ������ʱ����
	 */
    public static final String getDateTime(Date d) {
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

	/**
	 * ��ȡ������ʱ����
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d Ҫ��ȡ��������ʱ�������������ֵ
	 * @return ������ʱ����
	 */
    public static final String getDateTime(long d) {
        return getDateTime(new Date(d));
    }

	/**
	 * ��ȡ���ڵ�����Ϣ
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d Ҫ��ȡ������Ϣ������ֵ
	 * @return ����Ϣ
	 */
    public static final String getWeek(Date d) {
        final String dayNames[] = {"������", "����һ", "���ڶ�", "������", "������", "������",
                                  "������"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayNames[dayOfWeek - 1];
    }
    
	/**
	 * ��ȡ���ڵ�����Ϣ
	 * @see <pre><b>Modifier:</b>������
	 * <p/>
	 * <b>Algorithm:</b>
	 * <b>Last_modify__date:</b>2007-03-16
	 * </pre>
	 * 
	 * @param d Ҫ��ȡ������Ϣ����������ֵ
	 * @return ����Ϣ
	 */
    public static final String getWeek(long d) {
        return getWeek(new Date(d));
    }


    /**
     * 
     * @param prefix:
     * @return
     */
    public static final String getRondom(String prefix){
    	String result = null;
    	
    	result = prefix + "_"+String.valueOf(System.currentTimeMillis())+"_"+random.nextInt();
    	return result;
    }

}

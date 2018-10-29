package swz.infra.tools.string;

import swz.infra.tools.date.DateUtil;

public class StrUtil {
	public StrUtil() {
	}

	public static int Count(String s, String t) {
		int i = -t.length();
		int k = 0;
		for (; (i = s.indexOf(t, i + t.length())) != -1; k++) {
		}
		return k;
	}

	public static boolean Find(String[] s, String[] t) {
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < t.length; j++) {
				if (s[i].equals(t[j])) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean Find(String[] s, String t) {
		for (int i = 0; i < s.length; i++) {
			if (s[i].equals(t)) {
				return true;
			}
		}
		return false;
	}

	public static String[] split(String s, String t) {
		// s:mother string,t:spliter
		// get number of substrings
		int i = 0;
		int k = 1;
		for (; i < s.length();) {
			i = s.indexOf(t, i);
			if (i == -1) {
				break;
			} else {
				i += t.length();
			}
			k++;
		}
		// System.out.println(k);
		// get all subtrings
		String[] v = new String[k];
		i = 0;
		k = 0;
		for (; i < s.length();) {
			int h = s.indexOf(t, i);
			if (h == -1) {
				// System.out.println(i);
				v[k] = s.substring(i);
				k++;
				break;
			} else {
				// System.out.print(i);
				// System.out.print(",");
				// System.out.println(h);
				if (i == h) {
					v[k] = "";
					// System.out.print(k);
				} else {
					v[k] = s.substring(i, h);
				}
				i = h + t.length();
				k++;
			}
		}
		if (k < v.length) {
			v[k] = "";
		}
		return v;
	}

	public static String[] split(String s, String t, int n) {
		// s:mother string,t:spliter
		// get number of substrings
		int i = 0;
		int k = 1;
		for (; i < s.length() && k < n;) {
			i = s.indexOf(t, i);
			if (i == -1) {
				break;
			} else {
				i += t.length();
			}
			k++;
		}
		// get all subtrings
		String[] v = new String[k];
		i = 0;
		k = 0;
		for (; i < s.length();) {
			if (k == n - 1) {
				v[k] = s.substring(i);
				k++;
				break;
			}
			int h = s.indexOf(t, i);
			if (h == -1) {
				v[k] = s.substring(i);
				k++;
				break;
			} else {
				if (i == h) {
					v[k] = "";
				} else {
					v[k] = s.substring(i, h);
				}
				i = h + t.length();
				k++;
			}
		}
		if (k < v.length) {
			v[k] = "";
		}
		return v;
	}

	public static String[] splitRev(String s, String t, int n) {
		// s:mother string,t:spliter
		// get number of substrings
		int i = s.length() - 1;
		int k = 1;
		for (; i >= 0 && k < n;) {
			i = s.lastIndexOf(t, i);
			if (i == -1) {
				break;
			} else {
				i--;
			}
			k++;
		}
		// get all subtrings
		String[] v = new String[k];
		i = s.length() - 1;
		k = 0;
		for (; i >= 0;) {
			if (k == n - 1) {
				v[k] = s.substring(0, i + 1);
				k++;
				break;
			}
			int h = s.lastIndexOf(t, i);
			if (h == -1) {
				v[k] = s.substring(0, i + 1);
				k++;
				break;
			} else {
				if (i == h) {
					v[k] = "";
				} else {
					v[k] = s.substring(h + t.length(), i + 1);
				}
				i = h - 1;
				k++;
			}
		}
		if (k < v.length) {
			v[k] = "";
		}
		return v;
	}

	public static String[] splitPath(String s, String t) {
		String r[] = new String[Count(s, t) + 1];
		int k = s.length();
		r[0] = s;
		for (int i = 1; (k = s.lastIndexOf("/", k - 1)) != -1; i++) {
			r[i] = s.substring(0, k);
		}
		return r;
	}

	public static boolean canUse(String s) {
		if (s == null) {
			return false;
		} else {
			return !s.equals("");
		}
	}

	public static String replace(String s, String o, String r) {
		if (s == null) {
			return null;
		} else if (s.equals("")) {
			return s;
		} else {
			String[] ss = split(s, o);
			String n = "";
			int k = ss.length - 1;
			for (int i = 0; i < k; i++) {
				n += ss[i] + r;
			}
			n += ss[k];
			return n;
		}
	}

	public static String shell(String s) {
		if (s == null) {
			return null;
		} else if (s.equals("")) {
			return s;
		} else {
			return s.substring(1, s.length() - 1);
		}
	}

	public static String getStr(String s, int times) {
		String t = "";
		for (int i = 0; i < times; i++) {
			t += s;
		}
		return t;
	}

	public static String getStr(String s, String sep, int times) {
		if (times <= 0) {
			return "";
		}
		String t = s;
		for (int i = 1; i < times; i++) {
			t += sep + s;
		}
		return t;
	}

	public static String getSQLParms(int times) {
		return getStr("?", ",", times);
	}

	public static String getStr(String s[], String sep) {
		if (s == null) {
			return "";
		}
		String t = s[0];
		for (int i = 1; i < s.length; i++) {
			t += sep + s[i];
		}
		return t;
	}

	public static String[] Merge(Object[] obs) {
		int len = 0;
		for (int i = 0; i < obs.length; i++) {
			if (obs[i].getClass().isArray()) {
				len += ((String[]) obs[i]).length;
			} else {
				len += 1;
			}
		}
		String[] n = new String[len];
		int c = 0;
		for (int i = 0; i < obs.length; i++) {
			if (obs[i].getClass().isArray()) {
				String[] t = (String[]) obs[i];
				for (int k = 0; k < t.length; k++) {
					n[c++] = t[k];
				}
			} else {
				n[c++] = obs[i].toString();
			}
		}
		return n;
	}

	public static String[] Merge(String[] s, String[] t) {
		if (s == null) {
			return t;
		}
		if (t == null) {
			return s;
		}
		String[] n = new String[s.length + t.length];
		for (int i = 0; i < s.length; i++) {
			n[i] = s[i];
		}
		for (int i = 0; i < t.length; i++) {
			n[s.length + i] = t[i];
		}
		return n;
	}

	public static String[] Merge(String s, String[] t) {
		return Merge(new String[] { s }, t);
	}

	public static String[] Merge(String[] s, String t) {
		return Merge(s, new String[] { t });
	}

	public static String stringRemoveBeginZero(String str) {
		/*
		 * try { while(str.substring(0, 1).equals("0")) { str = str.substring(1,
		 * str.length()); } } catch(Exception e) { return "0"; } return str;
		 */
		return str.replaceFirst("^0*", "");

	}
	
	public static int getNowHH() {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("hh");
		return new Integer(DateUtil.getDateFormat(new java.util.Date(), "HH")).intValue();
	}

	public static String getRadomS() {
		String stime = DateUtil.getDateFormat(new java.util.Date(), "yyyyMMddHHmmssSSSSSS");
		String sNum = String.valueOf(Math.abs(new java.util.Random().nextInt()) % 100000);
		return stime + sNum;
	}

}

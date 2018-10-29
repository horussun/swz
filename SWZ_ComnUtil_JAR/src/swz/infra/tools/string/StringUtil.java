/**
 * 
 */
package swz.infra.tools.string;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	public static final String[] NumberCN = {"��","һ","��","��","��","��","��","��","��","��","ʮ"};
	public static final String[] NumberCN_BIG = {"��","Ҽ","��","��","��","��","½","��","��","��","ʰ","��","Ǫ","�f"};

	
	/**
	 * 
	 * @param separatedString: A string separated by delimiter.
	 * @param delim: delimiter
	 * @return:return a elments number which match regex of delimiter,
	 */
	public static String[]  getArrayFromString(String separatedString,String delim){
		String sWork = separatedString+delim;
		HashMap mapSpecialRegex=getSpecialRegexFormat();

		
		if(mapSpecialRegex.get(delim)!=null){
			delim=mapSpecialRegex.get(delim).toString();
		}
		
		String[] arrWork=sWork.split(delim);
//		System.out.println("num: "+arrWork.length);
//		for(int i=0;i<arrWork.length;i++){
//		   System.out.println("["+i+"]= "+arrWork[i]);	
//		}
		return arrWork;
	}
	/**
	 * 
	 * @param separatedString: A string separated by delimiter.
	 * @param delim: delimiter
	 * @return:return a elments number which match regex of delimiter,
	 */
	public static int  getArrayNumFromString(String separatedString,String delim){
		String sWork = separatedString+delim;
		HashMap mapSpecialRegex=getSpecialRegexFormat();

		
		if(mapSpecialRegex.get(delim)!=null){
			delim=mapSpecialRegex.get(delim).toString();
		}
		
		String[] arrWork=sWork.split(delim);
//		System.out.println("num: "+arrWork.length);
//		for(int i=0;i<arrWork.length;i++){
//		   System.out.println("["+i+"]= "+arrWork[i]);	
//		}
		return arrWork.length;
	}
	
	
	/**
	 * 
	 * @return: return a hashmap contain special regex format 
	 *    delimiter          regFormat
	 *    "+"              "[+]"  or   "\\x"
	 */
	private static HashMap getSpecialRegexFormat(){
		HashMap specialRegexFormat=new HashMap();
		specialRegexFormat.put("+","[+]");
		
		return specialRegexFormat;
	}



	/**
	 * 
	 * @Fun:��һ���ַ�����ָ���������ַ�֮�������
	 * @Para:psResource:������
	 *       pnBegin   �������п�ʼλ�ã�
	 *       psBegin   :��ʼ�Ӵ���
	 *       psEnd     :�����Ӵ���
	 */
	
	public static String getSubString(String psResource, int pnBegin,String psBegin, String psEnd) {
		int nBegin,nEnd;
		String sResource = psResource.substring(pnBegin);
		nBegin=sResource.indexOf(psBegin,pnBegin);
		nEnd=sResource.indexOf(psEnd,nBegin);

		if(nBegin==-1){
			nBegin=0;
		}
		else if(nEnd==-1){
			nEnd=sResource.length();
		}
    	return psResource.substring(nBegin+psBegin.length(), nEnd);	
	}

	/**
	 * <p>
	 * ��psResource�� if pnLen<=psResource.length return psResource else
	 * ���س���ΪpnLen���´����ճ�������psHolder���㣬Align��ʶ���Ҷ����־
	 * </p>
	 * 
	 * @param psResource
	 * @para pnLen: expected len 
	 * @param pnAlign��0�������
	 *            other���Ҷ���
	 * @param pcHolder: ռλ��
	 */
	public static String getFormatStr(String psResource, int pnLen,
			int pnAlign, String psHolder) {
		String sWork="";

		int nWork = psResource.length();
		if (nWork >= pnLen)
			return psResource;
		else {
			nWork = pnLen - nWork;
			for (int i = 0; i < nWork; i++) {
				sWork = sWork + psHolder;
			}
			if (pnAlign == 0)
				return psResource + sWork;
			else
				return sWork + psResource;
		}
	}
   
	/**
	 * Specified the padded str, pad num , return a string which is padded with the parameter
	 * @param strPad
	 * @param nPadNum
	 * @param Len
	 * @return
	 */
	public static String initStr(String strPad, int nPadNum,int Len) {
	    String result = "";
	    StringBuffer sb = new StringBuffer();
	    for (int i=0;i<nPadNum;i++) {
	       sb.append(strPad);	
	    }
		return sb.toString(); 	
	}

	 public static boolean IsNull(Object o) {
		if (o == null) {
		  return true;
		}
		else if (o.toString().equals("")) {
		  return true;
		}
		else {
		  return false;
		}
	  }
	 
	  public static String getStr(Object o) {
		if (o == null) {
		  return "";
		}
		else {
		  return o.toString();
		}
	  }
	  
		/**
		 * 
		 * @Fun: Given psResource, replace the content between of speicified left&right delimiter 
		 */

		public static String replaceSubString(String psResource, int pnBegin,
				String psBegin, String psEnd,String psNewValue) {

//			System.out.println("original content:" + psResource);
			int nBegin, nEnd;
			String result="";
			
			String sResource = psResource.substring(pnBegin);
			nBegin = sResource.indexOf(psBegin, 0);
			nEnd = sResource.indexOf(psEnd, nBegin);

			if (nBegin == -1) {
				nBegin = 0;
			} else if (nEnd == -1) {
				nEnd = sResource.length();
			}
			
			result=psResource.substring(0,pnBegin+nBegin+psBegin.length())+psNewValue+psResource.substring(pnBegin+nEnd);
//			System.out.println("result content:" + psResource);
			return result;
		}
		
		
		/**
		 * 移去前面的零
		 * @param str
		 * @return
		 */
		public static String stringRemoveBeginZero(String str) {
//			try {
//				while(str.substring(0, 1).equals("0")) {
//					str = str.substring(1, str.length());
//				}
//			} catch(Exception e) {
//				return "0";
//			}
//			return str;
			return str.replaceFirst("^0*", "");
		}
		
		public static Long isMatch(String inStr, String patStr) {

			int result = 0;

			Pattern pattern = Pattern.compile(patStr);
			Matcher matcher = pattern.matcher(inStr);
			if (matcher.find()) {
				result = 1;
			}

			return new Long(result);

		}
		
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "F2 RB";
		String p = "\\bF2 RB\\b";
		System.out.println("s: " + s + ", p: " + p);
		System.out.println("result: " + isMatch(s, p));
		
		String s2 = "RA ";
		String p2 = "\\bRA \\b";
		System.out.println("s2: " + s2 + ", p2: " + p2);
		System.out.println("result: " + isMatch(s2, p2));

	}

}

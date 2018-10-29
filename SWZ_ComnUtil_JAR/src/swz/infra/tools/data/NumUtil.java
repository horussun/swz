/**
 * 
 */
package swz.infra.tools.data;

import java.text.DecimalFormat;

/**
 * General numeric utility;
 * @author Michael
 * example: 
 */
public class NumUtil {
	static DecimalFormat dfmt = new DecimalFormat("#,###.00");
	static String sWork;
	/**
	 * Get the specified format of psRes
	 * @param psRes
	 * @param psPattern
	 * @return
	 */	
	public static String sGetFloatFormated(String psRes,String psPattern){
		if(psPattern.equals("")){
			sWork=dfmt.format(Float.valueOf(psRes)).toString();	
		}
		else{
			DecimalFormat dfmtWork = new DecimalFormat(psPattern);
			sWork=dfmtWork.format(Float.valueOf(psRes)).toString();
		}
		return sWork;
		
	}
	/**
	 * Get the specified format of pfRes
	 * @param pfRes
	 * @param psPattern
	 * @return 
	 *  String f1= "100.101";
     *  String f2=NumUtil.sGetFloatFormated(f1, "##");
     *  :100;
	 */
	public static String sGetFloatFormated(float pfRes,String psPattern){
		if(psPattern.equals("")){
			sWork=dfmt.format(pfRes);	
		}
		else{
			DecimalFormat dfmtWork = new DecimalFormat(psPattern);
			sWork=dfmtWork.format(pfRes);
		}
		return sWork;
	}
	
	
	/**
	 * Get the specified format of pfRes
	 * @param pfRes
	 * @param psPattern
	 *        "0.000";
	 * @return 
	 */
	public static String transfDouble2Str(double pdRes,String psPattern){
		String sWork="";
        try{
    		if(psPattern.equals("")){
    			sWork=dfmt.format(pdRes);	
    		}
    		else{
    			DecimalFormat dfmtWork = new DecimalFormat(psPattern);
    			sWork=dfmtWork.format(pdRes);
    		}
        }
        catch(Exception ex){
        	//ex.printStackTrace();
        	sWork="0";
        }
		return sWork;
		//modify by sunjie 20120817-4:27
		/*
		String sWork=null;
		if(psPattern.equals("")){
			sWork=dfmt.format(pdRes);	
		}
		else{
			DecimalFormat dfmtWork = new DecimalFormat(psPattern);
			sWork=dfmtWork.format(pdRes);
		}
		return sWork;
		*/
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

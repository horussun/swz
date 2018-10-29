package com.ms.freemarker;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SvcUtil {
	public ResourceBundle rsBundle = ResourceBundle.getBundle("svcCFG");
	
	public void getSvcInfo() {
		String svcFile = rsBundle.getString("svc.file");
		String[] svcSheetNMs = getArrayFromString(rsBundle.getString("svc.sheetNMs"), ",");
		int rowS = Integer.parseInt(rsBundle.getString("svc.rowS"));
		int iNM = Integer.parseInt(rsBundle.getString("svc.nm"));
		try {
			//Workbook svcWb = SSWorkbookHelper.getWorkbook(svcFile);
			for(int i=0;i<svcSheetNMs.length;i++) {
				Sheet svcSheet = SSWorkbookHelper.getWorksheet(svcFile, svcSheetNMs[i]);
				int RowE = svcSheet.getLastRowNum();
				for(int j=rowS;j<=RowE;j++) {
					System.out.println(getSvcValues(svcSheet,iNM,j));
				}
			}
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getSvcValues(Sheet srcSheet,int col, int row) {
		return SSWorkbookHelper.getCellValueAsString(srcSheet, col, row);
	}
	
	public static String[] getArrayFromString(String separatedString,String delim){
		String sWork = separatedString+delim;
		HashMap<String, String> mapSpecialRegex=getSpecialRegexFormat();

		if(mapSpecialRegex.get(delim)!=null){
			delim=mapSpecialRegex.get(delim).toString();
		}
		
		String[] arrWork=sWork.split(delim);
		return arrWork;
	}
	
	private static HashMap<String, String> getSpecialRegexFormat(){
		HashMap<String, String> specialRegexFormat=new HashMap<String, String>();
		specialRegexFormat.put("+","[+]");
		return specialRegexFormat;
	}
	
	public static String getCurrDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern("YYYY-MM-dd");
		return sdf.format(new Date(System.currentTimeMillis()));
	}

	public static String toFirstUpCase(String str) {
		char[] cs=str.toCharArray();
		for(int i=0;i<cs.length;i++) {
			if(i==0) {
				if(cs[0]>='a' && cs[0] <= 'z') {
					cs[0]-=32;
				}
			} else {
				if(Character.isLetter(cs[i])) {
					if(Character.isLetter(cs[i-1])) {
						if(cs[i]>='A' && cs[i] <= 'Z') {
							cs[i]+=32;
						}
					} else {
						if(cs[i]>='a' && cs[i] <= 'z') {
							cs[i]-=32;
						}
					}
				}
			}
		}
		return String.valueOf(cs);
	}

}
